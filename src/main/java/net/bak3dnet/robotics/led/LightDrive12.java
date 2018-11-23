package net.bak3dnet.robotics.led;

import java.util.Arrays;

import edu.wpi.first.wpilibj.SerialPort;
import net.bak3dnet.robotics.led.modules.LedControlModule;

public class LightDrive12 {

    private ControllerUpdatingService updatingService;
    private Thread serviceThread;

    private Channel channel1;
    private Channel channel2;
    private Channel channel3;
    private Channel channel4;

    private Channel[] channels;

    private SerialPort port;

    public LightDrive12(SerialPort.Port portToUse) {

        port = new SerialPort(115200, portToUse);

        channel1 = channel2 = channel3 = channel4 = new Channel();

        channels = new Channel[4];
        channels[0] = channel1;
        channels[1] = channel2;
        channels[2] = channel3;
        channels[3] = channel4;

        updatingService = new ControllerUpdatingService(this);
        serviceThread = new Thread(updatingService, "LED Updating Service");
        serviceThread.start();

    }

    private byte calculateChecksum(byte[] toCalculate) {

        byte sum = 0;

        for(byte b: toCalculate) {

            sum +=b;

        }

        return sum;

    }

    private void writeToSerialBus() {

        byte[] dataOut = new byte[14];
        dataOut[0] = (byte) 0xAA;
        for(int i = 0; i < 4; i++) {

            dataOut[3*i-2] = channels[i].getColorValue('g');
            dataOut[3*i-1] = channels[i].getColorValue('r');
            dataOut[3*i] = channels[i].getColorValue('b');

        }
        
        dataOut[13] = calculateChecksum(dataOut);

        port.write(dataOut, 14);

    }

    private class ControllerUpdatingService implements Runnable {

        LightDrive12 lightDrive;
        Channel[] channels;
        long previousTimes[];

        public ControllerUpdatingService(LightDrive12 lDrive12) {

            lightDrive = lDrive12;
            channels = lightDrive.getChannels();

            Arrays.fill(previousTimes, System.currentTimeMillis());

        }

        @Override
        public void run() {

            while(true) {
                
                for(int i = 0; i<4;i++) {

                    long currentTimeMillis = System.currentTimeMillis();
                    long deltaTime = currentTimeMillis - previousTimes[i];
                    channels[i].updateColorValues(deltaTime);
                    previousTimes[i] = currentTimeMillis;

                }

                lightDrive.writeToSerialBus();

                try{

                    Thread.sleep(1);

                } catch(InterruptedException e) {

                    break;

                }

            }

        }

    }

    public void setChannelModule(int channelId, LedControlModule module) {

        serviceThread.interrupt();

        switch(channelId) {

            case 1: channel1.setChannelModule(module);
                break;
            case 2: channel2.setChannelModule(module);
                break;
            case 3: channel3.setChannelModule(module);
                break;
            case 4: channel4.setChannelModule(module);
                break;
            default: throw new IllegalArgumentException("There are only four channels on the device. Count naturally.");

        }

        serviceThread.start();        

    }

    public Channel[] getChannels() {

        return channels;

    }


}
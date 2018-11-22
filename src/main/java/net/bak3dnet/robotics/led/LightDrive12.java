package net.bak3dnet.robotics.led;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import net.bak3dnet.robotics.led.dynamic.DynamicControlBase;

public class LightDrive12 {

    SerialPort port;

    private List<Byte> channel1, channel2, channel3, channel4;
    private byte[] array;

    private DynamicLightingManager dLightingManager;
    private DynamicControlBase activeModuleChannel1;
    private DynamicControlBase activeModuleChannel2;
    private DynamicControlBase activeModuleChannel3;
    private DynamicControlBase activeModuleChannel4;

    private Thread dynamicLightingThread;

    private static class DynamicLightingManager implements Runnable {

        LightDrive12 lightDrive;

        public DynamicLightingManager(LightDrive12 lightDrive) {
            this.lightDrive = lightDrive;
        }

        public void run() {
            long previousTime = System.currentTimeMillis();
            while(true){

                long currentTime = System.currentTimeMillis();
                short deltaTime = (short)(currentTime - previousTime);
                lightDrive.getActiveModule(1).task(lightDrive,deltaTime);
                currentTime = System.currentTimeMillis();
                deltaTime = (short)(currentTime - previousTime);
                lightDrive.getActiveModule(1).task(lightDrive,deltaTime);
                currentTime = System.currentTimeMillis();
                deltaTime = (short)(currentTime - previousTime);
                lightDrive.getActiveModule(1).task(lightDrive,deltaTime);
                currentTime = System.currentTimeMillis();
                deltaTime = (short)(currentTime - previousTime);
                lightDrive.getActiveModule(1).task(lightDrive,deltaTime);

                previousTime = currentTime;

                try {
                    Thread.sleep(1);
                    //logger.debug("Loop Completed");
				} catch (InterruptedException e) {
                    break;
                }

            }

        }
    }

    public LightDrive12(Port portToUse) {

       port = new SerialPort(115200, portToUse);

       channel1.add(new Byte((byte)0));
       channel1.add(new Byte((byte)0));
       channel1.add(new Byte((byte)0));

       channel2 = channel3 = channel4 = channel1;

       array = new byte[14];

       dLightingManager = new DynamicLightingManager(this);

    }

    private Byte calcChecksum(List<Byte> bytesToCalculate) {

        byte checkSum = 0;
        for(byte i =0; i <13;i++) {

            checkSum += bytesToCalculate.get(i);

        }
        
        return new Byte(checkSum);

    }

    public void setChannelData(int channelId, List<Byte> data) {

        if(data.size() != 3) {
            throw new IllegalArgumentException("You must have an list with a size of three.");
        }
        if(channelId < 0 || channelId > 4) {
            throw new IllegalArgumentException("There are only 4 channels on the controller.");
        }

        switch(channelId) {
            case 1:
                channel1 = data;
                break;
            case 2:
                channel2 = data;
                break;
            case 3:
                channel3 = data;
                break;
            case 4:
                channel4 = data;
                break;
        }

    }

    public void setChannelData(int channelId, String hexString) {

        List<Byte> data = new ArrayList<Byte>();

        data.add(Integer.decode(hexString.substring(2, 3)).byteValue());
        data.add(Integer.decode(hexString.substring(0, 1)).byteValue());
        data.add(Integer.decode(hexString.substring(4, 5)).byteValue());

        setChannelData(channelId, data);

    }

    public void setAllChannels(List<Byte> data) {

        for(byte i = 1; i <= 4; i++) {
            setChannelData(i, data);
        }

    }

    public void setPluralChannels(int[] channels, List<List<Byte>> data) {
        int iterations =0;
        for(int i: channels) {

            setChannelData(i, data.get(iterations));
            iterations++;

        }
        writeToDevice();
    }

    public void setPluralChannels(int[] channels, String[] hexStrings) {

        List<Byte> preList = new ArrayList<Byte>();
        List<List<Byte>> data = new ArrayList<List<Byte>>(); 

        for(int i = 0; i< channels.length;i++){
            preList.add(Integer.decode(hexStrings[i].substring(0, 1)).byteValue());
            preList.add(Integer.decode(hexStrings[i].substring(2, 3)).byteValue());
            preList.add(Integer.decode(hexStrings[i].substring(4, 5)).byteValue());

            data.add(preList);
        }

        setPluralChannels(channels, data);

    }

    public DynamicControlBase getActiveModule(int channel) {

        switch(channel) {

            case 1:
                return activeModuleChannel1;
            case 2:
                return activeModuleChannel2;
            case 3:
                return activeModuleChannel3;
            case 4:
                return activeModuleChannel4;
            default:
                throw new IllegalArgumentException("There are only four natural number channels");

        }

    }

    public int writeToDevice() {

        List<Byte> buffer = new ArrayList<Byte>();
        buffer.add(new Byte((byte)0xaa));
        buffer.addAll(channel1);
        buffer.addAll(channel2);
        buffer.addAll(channel3);
        buffer.addAll(channel4);
        buffer.add(calcChecksum(buffer));        

        for(byte j = 0; j < buffer.size(); j++) {

            array[j] = buffer.get(j);

        }
        
        return port.write(array, 14);

    }

    public void setActiveModule(int channel, DynamicControlBase module) {

        if(dynamicLightingThread != null) {
            
            //logger.debug("Interrupting taskCoordinator");
            dynamicLightingThread.interrupt();
        
        }
        
        //logger.debug("Setting active module");
        switch(channel) {

            case 1:
                activeModuleChannel1 = module;
                break;
            case 2:
                activeModuleChannel2 = module;
                break;
            case 3:
                activeModuleChannel3 = module;
                break;
            case 4:
                activeModuleChannel4 = module;
                break;

            default:
                throw new IllegalArgumentException("There are only four natural number channels");

        }
        //logger.debug("Creating new thread");
        dynamicLightingThread = new Thread(dLightingManager);
        //logger.info("Starting new thread");
        this.dynamicLightingThread.start();

    }
}
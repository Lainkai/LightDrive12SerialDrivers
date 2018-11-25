package net.bak3dnet.robotics.led;

import net.bak3dnet.robotics.led.modules.LedControlModule;
import net.bak3dnet.robotics.led.modules.AStaticColorModule;

public class Channel {

    private byte redValue;
    private byte greenValue;
    private byte blueValue;

    private LedControlModule activeModule;

    public Channel() {

        redValue = greenValue = blueValue = 0;
        activeModule = new AStaticColorModule("000");

    }

    public byte getColorValue(char colorId) {

        switch(colorId) {

            case 'r':
                return redValue;
            case 'g':
                return greenValue;
            case 'b':
                return blueValue;
            default: throw new IllegalArgumentException("The only color IDs are r, g, and b");

        }

    }

    public void updateColorValues(long deltaTime) {

        byte[] data = activeModule.updateColorData(deltaTime);

        redValue = data[0];
        greenValue = data[1];
        blueValue = data[2];

    }

    public void setChannelModule(LedControlModule module) {

        activeModule = module;

    }

    public LedControlModule getChannelMOdule() {

        return activeModule;

    }

}
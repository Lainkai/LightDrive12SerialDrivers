package net.bak3dnet.robotics.led.modules;

import net.bak3dnet.robotics.led.Color;

public class AStaticColorModule extends LedControlModule{

    public AStaticColorModule(byte[] colorArray) {

        red = colorArray[0];
        green = colorArray[1];
        blue = colorArray[2];

    } 

    public AStaticColorModule(String hexString) {

        this(Color.stringToByteArray(hexString));

    }

    @Override
    public void task(long deltaTime) {}



}
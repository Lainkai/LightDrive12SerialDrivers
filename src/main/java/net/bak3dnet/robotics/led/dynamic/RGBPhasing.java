package net.bak3dnet.robotics.led.dynamic;

import net.bak3dnet.robotics.led.LightDrive12;

public class RGBPhasing implements DynamicControlBase {

    private byte red;
    private byte green;
    private byte blue;

    private boolean greenG;
    private boolean blueG;
    private boolean redG;

    private boolean redD;
    private boolean greenD;
    private boolean blueD;

    public RGBPhasing() {
    
        close();

    }


    @Override
    public void task(LightDrive12 drive, long deltaTime) {


        if(red == 255) {

            blueD = true;
            redG = false;

        }

        if(green == 255) {

            greenG = false;
            redD = true;

        }

        if(blue == 255) {

            greenD = true;
            blueG = false;

        }

        if(red == 0) {

            blueG = true;
            redD = false;

        }

        if(green == 0) {

            redG = true;
            greenD = false;

        }

        if(blue == 0) {

            greenG = true;
            blueD = false;

        }

        if(redG) {

            red++;

        }

        if(greenG) {

            green++;

        }

        if(blueG) {

            blue++;
        
        }

        if(redD) {

            red--;

        }

        if(greenD) {

            green--;

        }

        if(blueD) {

            blue--;
        
        }
        
    }

    @Override
    public void close() {
        green = blue = (byte)0;
        red = (byte)255;

        blueG = redG = redD = greenD = blueD = false;
    }

}
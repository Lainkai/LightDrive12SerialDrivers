package net.bak3dnet.robotics.led;

public class Color {

    public static byte[] stringToByteArray(String toRGB) {

        byte[] colorArray = new byte[3];

        if(toRGB.charAt(0) == '#') {

            toRGB = toRGB.substring(1);

        }

        if(toRGB.length() == 6) {

            colorArray[0] = (Integer.decode(toRGB.substring(0, 1)).byteValue());
            colorArray[1] = (Integer.decode(toRGB.substring(2, 3)).byteValue());
            colorArray[2] = (Integer.decode(toRGB.substring(4, 5)).byteValue());

        } else {

            colorArray[0] = (byte) ((Integer.decode(toRGB.substring(0,0)))*17);
            colorArray[1] = (byte) ((Integer.decode(toRGB.substring(1,1)))*17);
            colorArray[2] = (byte) ((Integer.decode(toRGB.substring(2,2)))*17);

        }      

        return colorArray;
        
    }

}
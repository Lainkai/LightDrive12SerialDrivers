package net.bak3dnet.robotics.led;

public class Color {

    private byte red;
    private byte green;
    private byte blue;

    public static final Color SoftBlue = new Color("#0086f4");

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

    public Color(byte[] byteArray) {

        red = byteArray[0];
        green = byteArray[1];
        blue = byteArray[2];

    }
    
    public Color(String hexString) {

        this(stringToByteArray(hexString));

    }

    public byte[] getBytes() {

        byte[] out = {red,green,blue};
        return out;

    }

    public byte getRed(){
        return red;
    }
    public byte getGreen(){
        return green;
    }
    public byte getBlue(){
        return blue;
    }

}
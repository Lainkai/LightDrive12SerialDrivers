package net.bak3dnet.robotics.led;

public class Color {

    private byte red;
    private byte green;
    private byte blue;

    public static final Color SoftBlue = new Color("#0086f4");
    
    public static final Color BLUE = new Color("00F");
    public static final Color GREEN = new Color("0F0");
    public static final Color RED = new Color("F00");
    public static final Color BLACK = new Color("000");
    public static final Color WHITE = new Color("FFF");
    

    public static byte[] stringToByteArray(String toRGB) {

        byte[] colorArray = new byte[3];

        if(toRGB.charAt(0) == '#') {

            toRGB = toRGB.substring(1);

        }

        if(toRGB.length() == 6) {

            colorArray[0] = (byte) (Integer.parseInt(toRGB.substring(0, 2).trim(), 16));
            colorArray[1] = (byte) (Integer.parseInt(toRGB.substring(2, 4).trim(), 16));
            colorArray[2] = (byte) (Integer.parseInt(toRGB.substring(4).trim(), 16));

        } else {

            colorArray[0] = (byte) ((Integer.parseInt(toRGB.substring(0,1).trim(), 16))*17);
            colorArray[1] = (byte) ((Integer.parseInt(toRGB.substring(1,2).trim(), 16))*17);
            colorArray[2] = (byte) ((Integer.parseInt(toRGB.substring(2).trim(), 16))*17);

        }      

        return colorArray;
        
    }

    public Color(byte[] byteArray) {

        red = byteArray[0];
        green = byteArray[1];
        blue = byteArray[2];

    }

    public Color(int red, int green, int blue) {

        if(red > 255 || green > 255 || blue > 255 || red < 0|| green < 0 || blue < 0) {

            throw new IllegalArgumentException("You need to make sure the values are between 0 and 255");

        }

        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;

    }
    
    public Color(String hexString) {

        this(stringToByteArray(hexString));

    }

    public byte[] getBytes() {

        byte[] out = {red,green,blue};
        return out;

    }

    public int getRed(){
        return red & 0xff;
    }
    public int getGreen(){
        return green & 0xff;
    }
    public int getBlue(){
        return blue & 0xff;
    }

    @Override
    public String toString() {

        return Integer.toString(red & 0xff)+" "+Integer.toString(green & 0xFf)+" "+Integer.toString(blue & 0xff);

    }
}

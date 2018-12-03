package net.bak3dnet.robotics.led.modules;

public abstract class LedControlModule {

    protected byte red;
    protected byte green;
    protected byte blue;

    public byte[] updateColorData(long deltaTime) {

        task(deltaTime);
        byte[] out = {red, green, blue};
        return out;

    }

    public abstract void task(long deltaTime);

}
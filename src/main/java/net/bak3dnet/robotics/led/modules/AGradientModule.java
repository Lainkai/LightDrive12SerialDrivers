package net.bak3dnet.robotics.led.modules;

import net.bak3dnet.robotics.led.modules.util.GradientMap;

class AGradientModule extends LedControlModule {

    GradientMap colors;

    double scale;

    long position;

    boolean gradientToOriginal;
    public AGradientModule(GradientMap colorAndPercentage, double scale, boolean gradientToOriginal, double startAtPercent) {

        if(colors.size() < 2) {

            throw new IllegalArgumentException("You need at least two colors to gradient.");

        }

        this.scale = scale;

    }

    @Override
    public byte[] updateColorData(long deltaTime) {
        position += deltaTime * (long)scale;
        
        return colors.getCurrentColor(position);
    }

    @Override
    public void task(long deltaTime) {}

}
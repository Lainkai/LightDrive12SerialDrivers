package net.bak3dnet.robotics.led.modules;

import net.bak3dnet.robotics.led.modules.util.GradientMap;

public class AGradientModule extends LedControlModule {

    GradientMap colors;

    double scale;

    long position;

    boolean gradientToOriginal;
    public AGradientModule(GradientMap colorAndPercentage, double scale, double startAtPercent) {

        if(colors.size() < 2) {

            throw new IllegalArgumentException("You need at least two colors to gradient.");

        }

        this.scale = scale;

        position = colorAndPercentage.duration() *(long) startAtPercent;

    }

    public AGradientModule(GradientMap colorAndPercentage) {

        this(colorAndPercentage,1D,0D);

    }

    @Override
    public byte[] updateColorData(long deltaTime) {
        position += deltaTime * (long)scale;
        
        return colors.getCurrentColor(position).getBytes();
    }

    @Override
    public void task(long deltaTime) {}

}
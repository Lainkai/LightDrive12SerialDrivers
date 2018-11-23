package net.bak3dnet.robotics.led.modules;

import java.util.List;

import net.bak3dnet.robotics.led.Color;

class AGradientModule extends LedControlModule {

    public AGradientModule(List<Color> colors) {

        if(colors.size() < 2) {

            throw new IllegalArgumentException("You need at least two colors to gradient.");

        }

    }

    @Override
    public void task(long deltaTime) {

    }

    

}
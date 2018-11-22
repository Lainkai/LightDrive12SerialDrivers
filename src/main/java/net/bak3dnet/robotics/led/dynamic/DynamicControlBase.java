package net.bak3dnet.robotics.led.dynamic;

import net.bak3dnet.robotics.led.LightDrive12;

public interface DynamicControlBase {

    public void task(LightDrive12 lightDrive, long deltaTimeMillis);

    public void close();

}
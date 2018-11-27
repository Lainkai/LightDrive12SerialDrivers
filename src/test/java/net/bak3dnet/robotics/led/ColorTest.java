package net.bak3dnet.robotics.led;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.bak3dnet.robotics.led.modules.util.GradientMap;

public class ColorTest {

    @Test
    public void testColorConstructorTest() {

        Color color = new Color("f0f");
        assertEquals("Testing Colors", color.getRed(), (byte)255);

    }

    @Test
    public void testRemoveColorTest() {

        GradientMap map = new GradientMap(Color.BLUE,0L);

        map.put(new Color("F00"), 150L);
        map.put(Color.GREEN, 150L);
        map.remove(0);

        assertArrayEquals(map.getColor(2).getBytes(), Color.RED.getBytes());


    }

    @Test
    public void testLength() {


        GradientMap map = new GradientMap(Color.BLUE,0L);

        map.put(new Color("F00"), 150L);
        map.put(Color.GREEN, 150L);

        assertEquals(map.duration(), 300L);

    }

    @Test
    public void testRemovedLength() {

        GradientMap map = new GradientMap(Color.BLUE,0L);

        map.put(new Color("F00"), 150L);
        map.put(Color.GREEN, 150L);
        map.remove(0);

        assertEquals(150L, map.duration());

    }

    @Test
    public void testGetColorTest() {

        GradientMap map = new GradientMap(Color.BLUE,0L);
        //System.out.println(map.getColor(0).getBytes());
        //System.out.println(new Color("#00f").getBytes());
        assertArrayEquals(map.getColor(0).getBytes(), new Color("00F").getBytes());

    }

}
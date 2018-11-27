package net.bak3dnet.robotics.led;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.bak3dnet.robotics.led.modules.util.GradientMap;

public class ColorTest {

    @Test
    public void testCurrentColor() {
        
        GradientMap map = new GradientMap(new Color("F00"), 0L);
        map.put(new Color("0F0"), 150L);
        //System.out.println(map.duration());
        assertArrayEquals(Color.GREEN.getBytes(), map.getCurrentColor(150L).getBytes());

    }

    @Test
    public void testColorConstructorTest() {

        Color color = new Color("f0f");
        assertEquals("Testing Colors", color.getRed(), (byte)255);

    }

    @Test
    public void testPutMethod() {
    
        GradientMap map = new GradientMap();
        map.put(new Color("F00"), 0L);
        map.put(new Color("0F0"), 150L);
        map.put(new Color("00F"), 150L, 1);

        assertArrayEquals(new Color("00F").getBytes(), map.getColor(1).getBytes());
    
    }

    @Test
    public void testReplaceMethod() {
    
        GradientMap map = new GradientMap();
        map.put(new Color("F00"), 0L);
        map.put(new Color("0F0"), 150L);
        map.insert(new Color("00F"), 150L, 1);

        assertArrayEquals(new Color("00F").getBytes(), map.getColor(1).getBytes());
    
    }

    @Test
    public void testRemoveColorTest() {

        GradientMap map = new GradientMap(Color.BLUE,0L);

        map.put(new Color("F00"), 150L);
        map.put(Color.GREEN, 150L);
        map.remove(0);

        assertArrayEquals(Color.GREEN.getBytes(), map.getColor(1).getBytes());


    }

    @Test
    public void testLength() {


        GradientMap map = new GradientMap(Color.BLUE,0L);

        map.put(new Color("F00"), 150L);
        map.put(Color.GREEN, 150L);

        assertEquals(300L,map.duration());

    }

    @Test
    public void testRemovedLength() {

        GradientMap map = new GradientMap(Color.BLUE,0L);

        map.put(new Color("F00"), 150L);
        map.put(Color.GREEN, 150L);
        map.remove(1);

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
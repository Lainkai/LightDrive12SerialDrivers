package net.bak3dnet.robotics.led.modules.util;

import java.util.HashMap;
import java.util.Map;

import net.bak3dnet.robotics.led.Color;

public class GradientMap {

    private Map<Integer, Color> colors = new HashMap<Integer, Color>();
    private Map<Integer, Long> points = new HashMap<Integer, Long>();

    private Long durationOfMapMillis = 0L;

    public GradientMap(Color color, Long lengthAfterLastColor) {

        colors.put(0,color);
        points.put(0,lengthAfterLastColor);

    }

    public GradientMap() {}

    public void put(Color color, Long lengthAfterLastColor) {

        colors.put(colors.size(), color);
        points.put(points.size(), lengthAfterLastColor);

        durationOfMapMillis += lengthAfterLastColor;

    }

    public void put(Color color, Long lengthAfterLastColor, Integer index) {

        if(index > colors.size()) {

            throw new IllegalArgumentException("Please make sure the colors are in numerical order.");

        }

        colors.put(index, color);
        points.put(index, lengthAfterLastColor);
        lengthAfterLastColor += lengthAfterLastColor;

    }

    public void insert(Color color, Long lengthAfterLastColor, Integer index) {

        Color prevColor;
        Color currentColor = color;
        Long prevTime;
        Long currentTime = lengthAfterLastColor;
        for(int i = index; i < colors.size(); i++){

            prevColor = colors.get(i);
            prevTime = points.get(i);
            colors.put(i, currentColor);
            points.put(i, currentTime);
            currentColor = prevColor;
            currentTime = prevTime;            

        }

    }

    public Color getCurrentColor(Long positionInMilliseconds) {

        if(colors.size() < 2) {

            return colors.get(0);

        }

        if(positionInMilliseconds > durationOfMapMillis) {

            while(positionInMilliseconds >= durationOfMapMillis) {

                positionInMilliseconds -= durationOfMapMillis;

            }
            positionInMilliseconds = durationOfMapMillis - Math.abs(positionInMilliseconds);

        } else if(positionInMilliseconds < 0) { throw new IllegalArgumentException(); }

        Integer i = 0;
        Long preSub = 0L;
        while(true) {

            preSub = positionInMilliseconds;
            positionInMilliseconds -= points.get(i);
            if((positionInMilliseconds <= 0L)){
                //System.out.println(i);
                break;
            }
            i++;

        }

        Color nextColor = colors.get(i);
        Color previousColor = colors.get(i-1);

        Long percentOfNextColor = (preSub/points.get(i))*100;

        //System.out.println(percentOfNextColor);

        Long percentOfPreviousColor = (100-percentOfNextColor);

        //System.out.println(percentOfPreviousColor);

        byte[] out = {

            (byte) (Math.round((percentOfNextColor*nextColor.getRed()+percentOfPreviousColor*previousColor.getRed())/100)),

            (byte) (Math.round((percentOfNextColor*nextColor.getGreen()+percentOfPreviousColor*previousColor.getGreen())/100)),

            (byte) (Math.round((percentOfNextColor*nextColor.getBlue()+percentOfPreviousColor*previousColor.getBlue())/100))


        };

        //System.out.println((percentOfNextColor*nextColor.getGreen()));

        return new Color(out);

    }

    public void remove(int index) {

        durationOfMapMillis -= points.get(index);

        for(int i = index; i < colors.size(); i++) {

            if(colors.get(i+1) != null) {
                colors.put(i, colors.get(i+1));
                points.put(i, points.get(i+1));
            } else {
                colors.remove(i);
                points.remove(i);
                break;
            }
        }

    }

    public int size() {

        return colors.size();

    }

    public long duration() {

        return durationOfMapMillis;

    }

    public Color getColor(int index) {

        if(!colors.containsKey(index)) {
            return null;
        }
        return colors.get(index);

    }

}

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

        this.put(color, lengthAfterLastColor, colors.size());

    }

    public void put(Color color, Long lengthAfterLastColor, Integer position) {

        if(colors.containsKey(position)) {

            for(Integer i = colors.size(); i > position;i--) {

                colors.put(i+1,colors.get(i));
                points.put(i+1, points.get(i));
                
            }

        }

        colors.put(position, color);
        points.put(position, lengthAfterLastColor);

        durationOfMapMillis += lengthAfterLastColor;

    }

    public void replace(Color color, Long lengthAfterLastColor, Integer position) {

        colors.put(position, color);
        points.put(position, lengthAfterLastColor);

    }

    public byte[] getCurrentColor(Long positionInMilliseconds) {

        if(colors.size() < 2) {

            return colors.get(0).getBytes();

        }

        if(positionInMilliseconds > durationOfMapMillis) {

            while(positionInMilliseconds >= durationOfMapMillis) {

                positionInMilliseconds -= durationOfMapMillis;

            }
            positionInMilliseconds = durationOfMapMillis - Math.abs(positionInMilliseconds);

        } else if(positionInMilliseconds < 0) { throw new IllegalArgumentException(); }

        Integer i = 0;
        Long preSub = 0L;
        while(positionInMilliseconds >= 0) {

            preSub = positionInMilliseconds;
            positionInMilliseconds-= points.get(i);
            i++;

        }
        
        Color nextPoint = colors.get(i);
        Color previousPoint = colors.get(i-1);

        Long percentOfNextColor = (preSub/points.get(i))*100;
        Long percentOfPreviousColor = (100-percentOfNextColor);

        byte[] out = {

            (byte) ((percentOfNextColor*(nextPoint.getRed())+percentOfPreviousColor*(previousPoint.getRed()))/2),

            (byte) ((percentOfNextColor*(nextPoint.getGreen())+percentOfPreviousColor*(previousPoint.getGreen()))/2),

            (byte) ((percentOfNextColor*(nextPoint.getBlue())+percentOfPreviousColor*(previousPoint.getBlue()))/2),


        };

        return out;

    }

    public void remove(int colorPosition) {

        durationOfMapMillis -= points.get(colorPosition);
        put(colors.get(colorPosition+1), points.get(colorPosition+1), colorPosition);
        colors.remove(colors.size()-1);
        points.remove(points.size()-1);

    }

    public int size() {

        return colors.size();

    }

    public long duration() {

        return durationOfMapMillis;

    }

}

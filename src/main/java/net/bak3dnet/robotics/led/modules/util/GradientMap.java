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

    private int getColorDelta(int nextColor, int previousColor, long lengthBtwnColors, long transitive) {

      double numerator = nextColor - previousColor;

      double delta = numerator / lengthBtwnColors;

      float position = (float) ((double)delta*transitive);
      
      int finalOut = (int) Math.round(position);

      return finalOut + previousColor;

    }

    public Color getCurrentColor(double positionInMilliseconds) {

      int pointInGradient =(int) (positionInMilliseconds % duration());

      int pIGBackup = pointInGradient;

      int preSub = 0;
      int futureColorId = 0;

      while(pIGBackup > 0) {

        preSub = pIGBackup;
        pIGBackup -= points.get(futureColorId);
        futureColorId++;

      }

      int prevColorId;
      if(futureColorId == 0) {

        prevColorId = colors.size()-1;

      } else {
          prevColorId = futureColorId-1;
      }


      int currentRed = getColorDelta(colors.get(futureColorId).getRed(), colors.get(prevColorId).getRed(), points.get(futureColorId), (long)preSub);
      int currentGreen = getColorDelta(colors.get(futureColorId).getGreen(), colors.get(prevColorId).getGreen(), points.get(futureColorId), (long)preSub);
      int currentBlue = getColorDelta(colors.get(futureColorId).getBlue(), colors.get(prevColorId).getBlue(), points.get(futureColorId), (long)preSub);
      
      return new Color(currentRed,currentGreen,currentBlue);

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

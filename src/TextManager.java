import processing.opengl.*;
import processing.core.*;
import codeanticode.glgraphics.*;
import java.util.*;

public class TextManager {
    int currentTimestamp;
    double decayFactor = .98;
    List<TextFeature> texts;
    List<Integer> xPositions;
    List<Integer> yPositions;
    int lastUsed = 0;
    PGraphics g;

    public TextManager(PGraphics newG) {
        g = newG;
        texts = new ArrayList<TextFeature>();
        xPositions = new ArrayList<Integer>();
        xPositions.add(18);
        xPositions.add(18*2+5);
        xPositions.add(18*3+5);
        xPositions.add(18*4+5);
        yPositions = new ArrayList<Integer>();
        yPositions.add(5);
        yPositions.add(5);
        yPositions.add(5);
        yPositions.add(5);
    }

    public void addText(TextFeature newTF) {
        texts.add(newTF);
    }

    public void setTimestamp(int newTimestamp) {
        currentTimestamp = newTimestamp;
    }

    public int getTimestamp() {
        return currentTimestamp;
    }

    public void setDecayFactor(double newDecay) {
        decayFactor = newDecay;
    }

    public double getDecayFactor() {
        return decayFactor;
    }


    public void draw() {
        for (TextFeature t : texts) {
            if (t.getTimestamp() <= currentTimestamp) {
                int oldAlpha = t.getAlpha();
                t.setAlpha((int)(oldAlpha*decayFactor));
                t.draw(g, 0, 0);
            }
        }
    }



}

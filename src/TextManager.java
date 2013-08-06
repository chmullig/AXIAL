import processing.opengl.*;
import processing.core.*;
import codeanticode.glgraphics.*;
import java.util.*;

public class TextManager {
    int currentTimestamp;
    double decayFactor = .98;
    List<TextFeature> texts;
    PGraphics g;

    public TextManager(PGraphics newG) {
        g = newG;
        texts = new ArrayList<TextFeature>();
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
                t.draw(g);
            }
        }
    }



}

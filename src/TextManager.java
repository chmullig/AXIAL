import processing.opengl.*;
import processing.core.*;
import codeanticode.glgraphics.*;
import java.util.*;

public class TextManager {
    int currentTimestamp;
    double decayFactor = .985;
    List<TextFeature> texts;

    public TextManager() {
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
        System.out.println(texts.get(0));
    }



}

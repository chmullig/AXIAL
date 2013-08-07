import processing.opengl.*;
import processing.core.*;
import codeanticode.glgraphics.*;
import java.util.*;

public class TextManager {
    int currentTimestamp;
    double decayFactor = .999;
    List<TextFeature> texts;
    int xPositions[];
    int yPositions[];
    ArrayList<TextFeature> slots;
    Deque<Position> positions;
    int lastSlotUsed = 0;
    int lastChecked = 0;
    int numSlots = 5;
    PGraphics g;

    public TextManager(PGraphics newG) {
        g = newG;
        texts = new ArrayList<TextFeature>();
        slots = new ArrayList<TextFeature>();
        positions = new ArrayDeque<Position>();
        xPositions = new int[numSlots];
        yPositions = new int[numSlots];
        for (int i = 0; i < numSlots; i++) {
            Position p = new Position(5, 20*(i+1));
            positions.add(p);
        }
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

        for (int i = lastChecked; i < texts.size(); i++) {
            TextFeature t = texts.get(i);
            if (t.getTimestamp() <= currentTimestamp) {
                if (slots.size() == numSlots) {
                    for (int j = 0; j < slots.size(); j++) {
                        TextFeature choppingBlock = slots.get(j);
                        if (t.getScore()*t.getAlpha() > 1+3*choppingBlock.getScore()*choppingBlock.getAlpha()) {
                            t.setPosition(choppingBlock.getPosition());
                            slots.set(j, t);
                            break;
                        }
                    }
                } else {
                    t.setPosition(positions.remove());
                    slots.add(t);
                }
                lastChecked = i+1;
            }
        }

        for (Iterator<TextFeature> itr = slots.iterator(); itr.hasNext(); ) {
            TextFeature t = itr.next();
            int oldAlpha = t.getAlpha();
            t.setAlpha((int)(oldAlpha*decayFactor));
            t.draw(g);
        }
    }



}

import processing.opengl.*;
import processing.core.*;
import codeanticode.glgraphics.*;
import java.util.*;

public class FeatureManager {
    int currentTimestamp;
    double decayFactor = .999;
    List<Featurable> features;
    int xPositions[];
    int yPositions[];
    ArrayList<Featurable> slots;
    Deque<Position> positions;
    int lastSlotUsed = 0;
    int lastChecked = 0;
    int numSlots = 5;
    PGraphics g;

    public FeatureManager(PGraphics newG) {
        g = newG;
        features = new ArrayList<Featurable>();

        slots = new ArrayList<Featurable>();
        positions = new ArrayDeque<Position>();
        xPositions = new int[numSlots];
        yPositions = new int[numSlots];
        for (int i = 0; i < numSlots; i++) {
            Position p = new Position(5, 20*(i+1));
            positions.add(p);
        }
    }

    public void addFeature(Featurable newF) {
        features.add(newF);
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

        for (int i = lastChecked; i < features.size(); i++) {
            Featurable f = features.get(i);
            if (f.getTimestamp() <= currentTimestamp) {
                if (slots.size() == numSlots) {
                    for (int j = 0; j < slots.size(); j++) {
                        Featurable choppingBlock = slots.get(j);
                        if (f.getScore()*f.getAlpha() > 1+3*choppingBlock.getScore()*choppingBlock.getAlpha()) {
                            f.setPosition(choppingBlock.getPosition());
                            slots.set(j, f);
                            break;
                        }
                    }
                } else {
                    f.setPosition(positions.remove());
                    slots.add(f);
                }
                lastChecked = i+1;
            }
        }

        for (Iterator<Featurable> itr = slots.iterator(); itr.hasNext(); ) {
            Featurable f = itr.next();
            int oldAlpha = f.getAlpha();
            f.setAlpha((int)(oldAlpha*decayFactor));
            f.draw(g);
        }
    }



}

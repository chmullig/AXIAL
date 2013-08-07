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
    ArrayList<Position> positions;
    int lastSlotUsed = 0;
    int lastChecked = 0;
    int numSlots = 5;
    PApplet pa;

    public FeatureManager(PApplet pa) {
        this.pa = pa;
        features = new ArrayList<Featurable>();

        slots = new ArrayList<Featurable>();
        positions = new ArrayList<Position>();
        xPositions = new int[numSlots];
        yPositions = new int[numSlots];
        for (int i = 0; i < numSlots; i++) {
            Position p = new Position(5, 20*(i+1));
            positions.add(p);
        }
    }

    public void setNumSlots(int numSlots) {
        this.numSlots = numSlots;
        xPositions = new int[numSlots];
        yPositions = new int[numSlots];
        slots = new ArrayList<Featurable>();
        positions = new ArrayList<Position>();
    }

    public int getNumSlots() {
        return numSlots;
    }

    public void addPosition(Position p) {
        positions.add(p);
    }

    public void setPosition(int slot_id, Position p) {
        positions.set(slot_id, p);
    }

    public Position getPosition(int slot_id) {
        return positions.get(slot_id);
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
                    f.setPosition(positions.remove(0));
                    slots.add(f);
                }
                lastChecked = i+1;
            }
        }

        for (Iterator<Featurable> itr = slots.iterator(); itr.hasNext(); ) {
            Featurable f = itr.next();
            int oldAlpha = f.getAlpha();
            f.setAlpha((int)(oldAlpha*decayFactor));
            f.draw(pa.g);
        }
    }



}

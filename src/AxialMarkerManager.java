import processing.opengl.*;

import codeanticode.glgraphics.*;

import de.fhpotsdam.unfolding.mapdisplay.*;
import de.fhpotsdam.unfolding.utils.*;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.core.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.utils.*;

public class AxialMarkerManager extends MarkerManager<Marker> {
    int currentTimestamp;
    double decayFactor = .985;

    public AxialMarkerManager() {
        super();
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
        for (Marker marker : markers) {
            if (((FoursquareMarker)(marker)).getTimestamp() <= currentTimestamp) {
                int oldAlpha = ((FoursquareMarker)(marker)).getAlpha();
                if (oldAlpha > 0) {
                    ((FoursquareMarker)(marker)).setAlpha((int)(oldAlpha*decayFactor));
                    marker.draw(map);
                }
            }
        }
    }



}

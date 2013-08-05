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
        // REVISIT Why twice? Here and in drawOuter()?
        // To allow both; markers either implement one or the other.
        // Off-map cut-off depends on it.

        for (Marker marker : markers) {
            if (((FoursquareMarker)(marker)).getTimestamp() <= currentTimestamp) {
                int oldAlpha = ((FoursquareMarker)(marker)).getAlpha();
                ((FoursquareMarker)(marker)).setAlpha((int)(oldAlpha*decayFactor));
                marker.draw(map);
            }
        }
    }



}

import processing.core.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.marker.*;
import processing.opengl.*;

import codeanticode.glgraphics.*;

import de.fhpotsdam.unfolding.mapdisplay.*;
import de.fhpotsdam.unfolding.utils.*;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.core.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.utils.*;


public class FoursquareMarker extends SimplePointMarker {
    private int timestamp;
    private int alphaLevel = 255;

    public FoursquareMarker(Location location) {
        super(location);
    }

    public void setTimestamp(int newTimestamp) {
        timestamp = newTimestamp;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setAlpha(int newAlpha) {
        alphaLevel = newAlpha;
    }
    public int getAlpha() {
        return alphaLevel;
    }

    public void draw(PGraphics pg, float x, float y) {
        float offset1 = (float)(-0.0005+(Math.random()*(.001)));
        float offset2 = (float)(-0.0005+(Math.random()*(.001)));

        pg.pushStyle();
        pg.noStroke();
        pg.fill(236, 100, 90, alphaLevel/3);
        pg.ellipse(x+offset1, y+offset2, 10, 10);
        pg.fill(236, 100, 90, alphaLevel*2);
        pg.ellipse(x+offset1, y+offset2, 3, 3);
        pg.popStyle();
    }
}

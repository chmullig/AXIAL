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

public class FoursquareUserMarker extends SimplePointMarker {
    int timestamp;
    int alphaLevel = 255;
    Location target;
    float easing = 0.00015f;

    public FoursquareUserMarker() {
        super();
    }

    public FoursquareUserMarker(Location location) {
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

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public Location getTarget() {
        return target;
    }

    public void updateLocation() {
        Location diff = new Location(location.x - target.x, location.y - target.y);

        if (Math.abs(diff.x) > 1) {
             location.x += diff.x * easing;
        }

        if (Math.abs(diff.y) > 1) {
             location.y += diff.y * easing;
        }
        System.out.println("Position: " + location);
    }



    public void draw(PGraphics pg, int x, int y) {
        pg.pushStyle();
        pg.noStroke();
        pg.fill(236, 100, 90, alphaLevel/3);
        pg.ellipse(x, y, 10, 10);
        pg.fill(236, 100, 90, alphaLevel*2);
        pg.ellipse(x, y, 3, 3);
        pg.popStyle();
    }
}

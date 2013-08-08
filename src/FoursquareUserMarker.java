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
    private int timestamp;
    private int alphaLevel = 255;
    private Position current;
    private Position target;
    private float easing = 0.15;

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

    public void setPosition(Position current) {
        this.current = current;
    }

    public Position getPosition() {
        return current;
    }

    public void setTarget(Position target) {
        this.target = target;
    }

    public Position getTarget() {
        return target;
    }


    public void draw(PGraphics pg) {
        //calculate where the new x and new y should be

        //current.x, current.y
        //target.x and target.y

        Position diff = new Position(current.x - target.x, current.y - target.y);

        if (abs(diff.x) > 1) {
             current.x += diff.x * easing;
        }

        if (abs(diff.y) > 1) {
             current.y += diff.y * easing;
        }

        pg.pushStyle();
        pg.noStroke();
        pg.fill(236, 100, 90, alphaLevel/3);
        pg.ellipse(current.x, current.y, 10, 10);
        pg.fill(236, 100, 90, alphaLevel*2);
        pg.ellipse(current.x, current.y, 3, 3);
        pg.popStyle();
    }
}

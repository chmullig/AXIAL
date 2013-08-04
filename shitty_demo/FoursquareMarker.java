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
    pg.pushStyle();
    pg.noStroke();
    pg.fill(200, 200, 0, alphaLevel);
    pg.ellipse(x, y, 5, 5);
    pg.popStyle();
  }
}

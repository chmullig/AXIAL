import processing.opengl.*;

import codeanticode.glgraphics.*;

import de.fhpotsdam.unfolding.mapdisplay.*;
import de.fhpotsdam.unfolding.utils.*;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.tiles.*;
import de.fhpotsdam.unfolding.interactions.*;
import de.fhpotsdam.unfolding.ui.*;
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.core.*;
import de.fhpotsdam.unfolding.data.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.texture.*;
import de.fhpotsdam.unfolding.events.*;
import de.fhpotsdam.utils.*;
import de.fhpotsdam.unfolding.providers.*;


UnfoldingMap map;
String CMAPIKey = "fd518097b2c24b7bbafe6716c3ee8b18";
AbstractMapProvider provider1;
AbstractMapProvider provider2;
AbstractMapProvider provider3;
List<Feature> checkins;
List<Marker> checkinMarkers;
int initialTime = 1370059200;
int currentTime = initialTime;
int frameLength = 60*30;
int lastPos = 0;
int WIDTH = 800;
int HEIGHT = 600;
AxialMarkerManager mm;

void setup() {
    size(WIDTH, HEIGHT, GLConstants.GLGRAPHICS);
 
    provider1 = new OpenStreetMap.OpenStreetMapProvider();
    provider2 = new Microsoft.AerialProvider();
    provider3 = new OpenStreetMap.CloudmadeProvider(CMAPIKey, 23058);
    map = new UnfoldingMap(this, provider3);
    MapUtils.createDefaultEventDispatcher(this, map);
    map.zoomToLevel(13);
    map.panTo(new Location(40.738f, -74f));
    
    fill(0);  // Black
    // set up the font (system default sans serif)
    textFont(createFont("SansSerif",18));
    textAlign(LEFT);

    checkins = GeoJSONReader.loadData(this, "data/foursquare.geojson");
    Collections.sort(checkins, new FeatureByTimestampComparer());
    MarkerFactory mf = new MarkerFactory();
    mf.setPointClass(FoursquareMarker.class);
    
    checkinMarkers = mf.createMarkers(checkins);
    System.out.println(checkinMarkers.get(0));
    
    for(int i = 0; i < checkins.size(); i++) {
      ((FoursquareMarker)(checkinMarkers.get(i))).setTimestamp((Integer)(checkins.get(i).getProperty("timestamp")));
    }
    mm = new AxialMarkerManager();
    mm.addMarkers(checkinMarkers);
    
    map.addMarkerManager(mm);
}

void draw() {
  currentTime += frameLength;
  mm.setTimestamp(currentTime);
  
  map.draw();
  text(new Date((long)currentTime*1000).toString(), 5, HEIGHT-5);
}



void keyPressed() {
    if (key == '1') {
        map.mapDisplay.setProvider(provider1);
    } else if (key == '2') {
        map.mapDisplay.setProvider(provider2);
    } else if (key == '3') {
        map.mapDisplay.setProvider(provider3);
    }
}

public class FeatureByTimestampComparer implements Comparator<Feature> {
  //@Override
  public int compare(Feature x, Feature y) {
    // TODO: Handle null x or y values
    return compare((Integer)x.getProperty("timestamp"), (Integer)y.getProperty("timestamp"));
  }

  // I don't know why this isn't in Long...
  private int compare(int a, int b) {
    return a < b ? -1
         : a > b ? 1
         : 0;
  }
}

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
int time = 1370059200;
int frameLength = 60*60;
int frame = -100;

void setup() {
    size(800, 600, GLConstants.GLGRAPHICS);
 
    provider1 = new OpenStreetMap.OpenStreetMapProvider();
    provider2 = new Microsoft.AerialProvider();
    provider3 = new OpenStreetMap.CloudmadeProvider(CMAPIKey, 23058);
    map = new UnfoldingMap(this, provider3);
    MapUtils.createDefaultEventDispatcher(this, map);
    map.zoomToLevel(12);
    map.panTo(new Location(40.75f, -74f));

    checkins = GeoJSONReader.loadData(this, "data/foursquare.geojson");
    checkinMarkers = MapUtils.createSimpleMarkers(checkins);
}

void draw() {
    if (frame >= 0 && frame < checkinMarkers.size()) {
      map.addMarkers(checkinMarkers.get(frame));
    }
    frame++;
    map.draw();
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

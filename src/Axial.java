import processing.core.*;
import processing.xml.*;

//import javax.media.opengl.*;
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

import java.applet.*;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.awt.Image;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;
import java.util.regex.*;

public class Axial extends PApplet {

    UnfoldingMap map;
    String CMAPIKey = "fd518097b2c24b7bbafe6716c3ee8b18";
    AbstractMapProvider provider1;
    AbstractMapProvider provider2;
    AbstractMapProvider provider3;
    List<Feature> checkins;
    List<Marker> checkinMarkers;
    int initialTime = 1370040000;
    int endTime = 1376107200;
    int currentTime = initialTime;
    int frameLength = 60*30;
    int lastPos = 0;
    int WIDTH = 1024;
    int HEIGHT = 768;
    AxialMarkerManager mm;

    boolean sketchFullScreen() {
        return false;
    }

    public void setup() {
        size(WIDTH, HEIGHT, GLConstants.GLGRAPHICS);

        provider1 = new OpenStreetMap.OpenStreetMapProvider();
        provider2 = new Microsoft.AerialProvider();
        provider3 = new OpenStreetMap.CloudmadeProvider(CMAPIKey, 23058);
        map = new UnfoldingMap(this, provider3);
        MapUtils.createDefaultEventDispatcher(this, map);
        map.zoomToLevel(13);
        map.panTo(new Location(40.735f, -73.99f));

        fill(0);  // Black
        textFont(createFont("SansSerif",18));
        textAlign(LEFT);

        //Load Foursquare checkins
        checkins = GeoJSONReader.loadData(this, "foursquare.geojson");
        Collections.sort(checkins, new FeatureByTimestampComparer());
        MarkerFactory mf = new MarkerFactory();
        mf.setPointClass(FoursquareMarker.class);
        checkinMarkers = mf.createMarkers(checkins);

        for(int i = 0; i < checkins.size(); i++) {
            ((FoursquareMarker)(checkinMarkers.get(i))).setTimestamp((Integer)(checkins.get(i).getProperty("timestamp")));
        }
        mm = new AxialMarkerManager();
        mm.addMarkers(checkinMarkers);
        map.addMarkerManager(mm);
    }

    public void draw() {
        currentTime += frameLength;
        mm.setTimestamp(currentTime);
        map.draw();
        text(new Date((long)currentTime*1000).toString(), 5, HEIGHT-5);

        saveFrame("frames/frame-######.tif");
        if (currentTime > endTime) {
            exit();
        }
    }



    public void keyPressed() {
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
        private int compare(int a, int b) {
            return a < b ? -1
            : a > b ? 1
            : 0;
        }
    }
    static public void main(String args[]) {
        PApplet.main(new String[] { "--bgcolor=#666666", "--stop-color=#cccccc", "Axial" });
    }
}

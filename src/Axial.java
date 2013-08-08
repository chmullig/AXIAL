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
    int frameLength = 60*15;
    int lastPos = 0;
    int WIDTH = 1024;
    int HEIGHT = 768;
    int imageHeight = 200;
    int imagePadding = 15;
    static boolean saveFrames = true;
    AxialMarkerManager mm;
    FeatureManager fm;
    FeatureManager im;

    boolean sketchFullScreen() {
        return false;
    }

    public void setup() {
        size(WIDTH, HEIGHT, GLConstants.GLGRAPHICS);

        provider1 = new OpenStreetMap.OpenStreetMapProvider();
        provider2 = new Microsoft.AerialProvider();               
        provider3 = new OpenStreetMap.CloudmadeProvider(CMAPIKey, 104478);
        map = new UnfoldingMap(this, provider3);
        MapUtils.createDefaultEventDispatcher(this, map);
        map.zoomToLevel(13);
        Location palladium = new Location(40.733311f, -73.988156f);
        map.panTo(palladium);

        fill(0);  // Black
        textFont(createFont("SansSerif",18));
        textAlign(LEFT);


        fm = new FeatureManager(this);
        im = new FeatureManager(this);
        im.setNumSlots(6);
        int initialImage = fm.getPosition(fm.getNumSlots()-1).y+30;
        im.addPosition(new Position(5, initialImage, LEFT));
        im.addPosition(new Position(5, initialImage+imageHeight+imagePadding, LEFT));
        im.addPosition(new Position(5, initialImage+2*(imageHeight+imagePadding), LEFT));
        im.addPosition(new Position(WIDTH-5, initialImage, RIGHT));
        im.addPosition(new Position(WIDTH-5, initialImage+imageHeight+imagePadding, RIGHT));
        im.addPosition(new Position(WIDTH-5, initialImage+2*(imageHeight+imagePadding), RIGHT));


        //Load Foursquare checkins
        checkins = GeoJSONReader.loadData(this, "foursquare.geojson");
        Collections.sort(checkins, new FeatureByTimestampComparer());

        mm = new AxialMarkerManager();
        map.addMarkerManager(mm);

        Hashtable<String, FoursquareUserMarker> users = new Hashtable<String, FoursquareUserMarker>();

        for (int i = 0; i < checkins.size(); i++) {
            Feature c = checkins.get(i);
            Position p = new Position((Integer)(c.getProperty("lat")), (Integer)(c.getProperty("lng")));
            p.setTimestamp((Integer)(c.getProperty("timestamp")));
            String name = c.getStringProperty("name");
            if (users.containsKey(c.getStringProperty("name")) {
                users.get(name).addPosition(p);
            } else {
                FoursquareUserMarker user = new FoursquareUserMarker();
                user.addPosition(p);
                users.put(name, user);
            }


            m.setTimestamp((Integer)(c.getProperty("timestamp")));
            if (!c.getStringProperty("text").equals("") && GeoUtils.getDistance(palladium, ((PointFeature)c).getLocation()) < 10) {
                TextFeature tf = new TextFeature(c.getStringProperty("text") + " @ " + c.getStringProperty("venue"));
                tf.setTimestamp((Integer)(c.getProperty("timestamp")));
                int score = 0;
                score += (tf.getString().toLowerCase().contains("hackny")) ? 3 : 0;
                score += (Integer)(c.getProperty("likes"));
                tf.setScore(score);
                fm.addFeature(tf);
                //Add the image if it's here
                if (!c.getStringProperty("photo").equals("")) {
                    ImageFeature imgFeature = new ImageFeature(this, c.getStringProperty("photo"));
                    imgFeature.setTimestamp((Integer)(c.getProperty("timestamp")));
                    imgFeature.setScore(score);
                    imgFeature.setHeight(imageHeight);
                    im.addFeature(imgFeature);
                }
            }
        }
    }


    public void draw() {
        currentTime += frameLength;
        System.out.println(new Date((long)currentTime*1000).toString());

        mm.setTimestamp(currentTime);
        map.draw();
        fm.setTimestamp(currentTime);
        fm.draw();
        im.setTimestamp(currentTime);
        im.draw();



        //Clock
        pushStyle();
        textAlign(RIGHT);
        Date dt = new Date((long)currentTime*1000);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, a");
        String formattedDate = formatter.format(dt);
            //Background for clock
            pushStyle();
            stroke(255, 0);
            fill(255, 255, 255, 155);
            int clockSize = (int)(textWidth(formattedDate));
            rect(WIDTH-9-clockSize, 2, WIDTH-2, 22);
            popStyle();
        text(formattedDate, WIDTH-5, 18);
        popStyle();

        if (saveFrames) {
            saveFrame("frames/frame-######.tif");
        }
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
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--test")) {
                saveFrames = false;
            }
        }

        PApplet.main(new String[] { "--bgcolor=#666666", "--stop-color=#cccccc", "Axial" });
    }
}

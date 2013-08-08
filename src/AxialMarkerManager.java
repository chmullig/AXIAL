import processing.opengl.*;

import codeanticode.glgraphics.*;

import de.fhpotsdam.unfolding.mapdisplay.*;
import de.fhpotsdam.unfolding.utils.*;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.core.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.utils.*;
import java.util.*;

public class AxialMarkerManager extends MarkerManager<Marker> {
    int currentTimestamp;
    Hashtable<FoursquareUserMarker, List<Location>> userLocations;
    Hashtable<FoursquareUserMarker, List<Integer>> userTimes;

    public AxialMarkerManager() {
        super();
        userLocations = new Hashtable<FoursquareUserMarker, List<Location>>();
        userTimes = new Hashtable<FoursquareUserMarker, List<Integer>>();
    }

    public void addLocation(FoursquareUserMarker user, Location location, int time) {
        if (userLocations.containsKey(user)) {
            userLocations.get(user).add(location);
            userTimes.get(user).add(time);
        } else {
            List lp = new ArrayList<Location>();
            lp.add(location);
            userLocations.put(user, lp);

            List lt = new ArrayList<Integer>();
            lt.add(time);
            userTimes.put(user, lt);
        }
    }

    public void setTimestamp(int newTimestamp) {
        currentTimestamp = newTimestamp;
    }

    public int getTimestamp() {
        return currentTimestamp;
    }

    public void draw() {
        for (Enumeration<FoursquareUserMarker> e = userLocations.keys(); e.hasMoreElements();) {
            FoursquareUserMarker user = e.nextElement();
            List<Location> locations = userLocations.get(user);
            List<Integer> times = userTimes.get(user);
            for (int i = 0; i < locations.size(); i++) {
                if (times.get(i) <= currentTimestamp) {
                    user.setTarget(locations.get(i));
                }
                if (user.getTarget() == null) {
                    user.setLocation(locations.get(i));
                }
            }
            if (user.getTarget() != null) {
                user.updateLocation();
                user.draw(map);
            }
        }
    }

}

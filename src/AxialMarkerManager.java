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
    Dictionary<FoursquareUserMarker, List<Position>> userPositions;

    public AxialMarkerManager() {
        super();
        userPositions = new Hashtable<FoursquareUserMarker, List<Position>>();
    }

    public void addPosition(FoursquareUserMarker user, Position position) {
        if (userPositions.containsKey(user)) {
            userPositions.get(user).add(position);
        } else {
            userPositions.put(user, new List<Position>(position));
        }
    }

    public void setTimestamp(int newTimestamp) {
        currentTimestamp = newTimestamp;
    }

    public int getTimestamp() {
        return currentTimestamp;
    }

    public void draw() {
        for (Enumeration<FoursquareUserMarker> e = userPositions.key(); e.hasMoreElments();) {
            FoursquareUserMarker user = e.nextElement();
            List<Position> positions = userPositions.get(user);
            for (Position p : positions) {
                if (p.timestamp <= currentTimestamp) {
                    user.setTarget(p);
                }
            }
            user.draw(map);
        }
    }

}

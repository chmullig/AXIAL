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

public class TextFeature {
    private int timestamp;
    private int alphaLevel = 256;
    private int score = 0;
    private Position p;
    String message;

    public TextFeature(String s) {
        message = s;
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

    public void setString(String s) {
        message = s;
    }

    public String getString() {
        return message;
    }

    public void setScore(int s) {
        score = s;
    }

    public int getScore() {
        return score;
    }

    public void setPosition(Position p) {
        this.p = p;
    }

    public Position getPosition() {
        return p;
    }

    public void draw(PGraphics pg) {
        pg.pushStyle();
        pg.noStroke();
        pg.fill(0, 150, 187, alphaLevel);
        pg.text(message, p.x, p.y);
        pg.popStyle();
    }
}

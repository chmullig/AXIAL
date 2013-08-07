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

public class ImageFeature extends Featurable {
    String url;
    PImage img;
    PApplet pa;
    int height;

    public ImageFeature(PApplet pa, String s) {
        url = s;
        this.pa = pa;
        img = pa.loadImage(url);
    }

    public void setURL(String s) {
        url = s;
        img = pa.loadImage(url);
    }

    public String getURL() {
        return url;
    }

    public PImage getImage() {
        return img;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void draw(PGraphics pg) {
        pg.pushStyle();
        pg.tint(255, alphaLevel);
        img.resize(0, height);
        pg.stroke(255, 0);
        pg.fill(255, 255, 255, (int)(alphaLevel*.8));
        if (p.alignment == PConstants.LEFT) {
            pg.rect(p.x-3, p.y-3, img.width+6, img.height+6);
            pg.image(img, p.x, p.y);
        } else {
            pg.rect(p.x-img.width-3, p.y-3, p.x+6, img.height+6);
            pg.image(img, p.x-img.width, p.y);
        }
        pg.popStyle();
    }
}

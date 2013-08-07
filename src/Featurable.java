import processing.opengl.*;
import processing.core.*;
import codeanticode.glgraphics.*;
import java.util.*;

public abstract class Featurable {
    protected int timestamp;
    protected int alphaLevel = 256;
    protected int score = 0;
    protected Position p;
    protected int alignment = PConstants.LEFT;

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setAlpha(int alphaLevel) {
        this.alphaLevel = alphaLevel;
    }
    public int getAlpha() {
        return alphaLevel;
    }

    public void setScore(int score) {
        this.score = score;
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

    abstract public void draw(PGraphics pg);
}
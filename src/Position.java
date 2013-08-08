import processing.core.*;

public class Position {
    public int x;
    public int y;
    public int timestamp;
    int alignment = PConstants.LEFT;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, int alignment) {
        this.x = x;
        this.y = y;
        this.alignment = alignment;
    }
}

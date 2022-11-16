import java.awt.Color;

import edu.macalester.graphics.Rectangle;

public class Tile extends Rectangle {
    private Color color = Color.GRAY;
    private Boolean bomb;
    private int number;

    public Tile(int size) {
        super(0,0, size, size);
        this.setFillColor(color);
    }
}

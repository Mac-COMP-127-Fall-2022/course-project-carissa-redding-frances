import java.awt.Color;
import java.util.Random;

import edu.macalester.graphics.Rectangle;

public class Tile extends Rectangle {
    private Color color = Color.GRAY;
    private Boolean bomb;
    private int number = 1;

    private Random rand = new Random();

    public Tile(int size) {
        super(0,0, size, size);
        this.setFillColor(color);
        this.bomb = rand.nextBoolean();
    }

    public Boolean getBomb() {
        return bomb;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public String toString() {
        return ("Tile at: " + getX() + ", " + getY() + (bomb ? " is a bomb" : " has number" + number));
    }

}

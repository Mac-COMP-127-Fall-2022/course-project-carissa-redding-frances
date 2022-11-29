import java.awt.Color;

import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Tile extends Rectangle {
    private Color color = Color.GRAY;
    private Boolean bomb = false;
    private int number = 0;
    private boolean beenClicked = false;
    private boolean flagged = false;
    private Image flag = new Image("images/redflag.png");

    public Tile(int size) {
        super(0,0, size, size);
        this.setFillColor(color);
        flag.setMaxHeight(size);
        flag.setCenter(getCenter());
    }

    public Boolean getBomb() {
        return bomb;
    }
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public boolean clicked() {
        return beenClicked;
    }

    public boolean getFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flag) {
        flagged = flag;
    }

    public Image getFlag() {
        return flag;
    }

    public void setClicked(boolean clicked) {
        this.beenClicked = clicked;
    }
    public String toString() {
        return ("Tile at: " + getX() + ", " + getY() + (bomb ? " is a bomb" : " has number " + number));
    }
}

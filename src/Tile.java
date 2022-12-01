import java.awt.Color;

import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Tile extends GraphicsGroup {
    private Color[] hiddenColor = {Minesweeper.budGreen, Minesweeper.budGreen2};
    private Color clearedColor = Minesweeper.brownSugar;
    private Color[] bombColor = {Minesweeper.redOrange, Minesweeper.selectiveYellow};
    private Color fontColor = Minesweeper.vividSkyBlue;

    private Boolean bomb = false;
    private int number = 0;
    private boolean clicked = false;
    private boolean flag = false;
    private Image flagImage = new Image("images/redflag.png");
    private Rectangle tile;
    private static boolean chessboard = false;
    private GraphicsText numberAsObject = new GraphicsText();

    public Tile(double size) {
        tile = new Rectangle(0,0, size, size);
        tile.setFillColor(hiddenColor[chessboard? 0:1]);
        add(tile);
        flagImage.setMaxHeight(size);
        flagImage.setCenter(getCenter());
        chessboard = !chessboard;
    }

    /* --------------------------------- Visuals -------------------------------- */
    public void setFlag(boolean flag) {
        if(flag) {
            flagImage.setCenter(tile.getCenter());
            add(flagImage);
        } else {
            remove(flagImage);
        }
        this.flag = flag;
    }

    public void reveal(boolean gameOver) {
        if(bomb) {
            tile.setFillColor(bombColor[gameOver? 1:0]);
        } else {
            tile.setFillColor(clearedColor);
            if(number > 0) {
                numberAsObject = new GraphicsText(number + "");
                numberAsObject.setFillColor(fontColor);
                numberAsObject.setFont(FontStyle.PLAIN, tile.getHeight() * .5);
                numberAsObject.setCenter(tile.getCenter());
                add(numberAsObject);
            }
        }
    }

    /* ---------------------------------- Logic --------------------------------- */
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

    public boolean getClicked() {
        return clicked;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String toString() {
        return ("Tile at: " + getX() + ", " + getY() + (bomb ? " is a bomb" : " has number " + number));
    }
}

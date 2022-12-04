import java.awt.Color;

import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Tile extends GraphicsGroup {
    private Color[] hiddenColor = {Minesweeper.budGreen, Minesweeper.budGreen2};
    private Color[] clearedColor = {Minesweeper.brownSugar, Minesweeper.brownSugarShade1, Minesweeper.brownSugarTint1};
    private Color[] bombColor = {Minesweeper.redOrange, Minesweeper.selectiveYellow};
    private Color fontColor = Minesweeper.vividSkyBlue;
    private Color currentColor;

    private Boolean bomb = false;
    private int number = 0;
    private boolean clicked = false;
    private boolean flag = false;
    private Image flagImage = new Image("images/redflag.png");
    private Rectangle tile;
    private static boolean chessboard = false;
    private GraphicsText numberAsObject = new GraphicsText();
    AniManager animations;

    public Tile(double size, AniManager animations) {
        this.animations = animations;
        tile = new Rectangle(0,0, size, size);
        currentColor = hiddenColor[chessboard? 0:1];
        tile.setFillColor(currentColor);
        add(tile);
        flagImage.setMaxHeight(size);
        flagImage.setCenter(getCenter());
        chessboard = !chessboard;
    }

    /* --------------------------------- Visuals -------------------------------- */
    public void setFlag() {
        if(clicked && !this.flag) {
            return;
        }
        if(!flag) {
            flagImage.setCenter(tile.getCenter());
            add(flagImage);
        } else {
            remove(flagImage);
        }
        clicked = !clicked;
        flag = !flag;
    }

    public void reveal(boolean gameOver) {
        if(bomb) {
            currentColor = bombColor[gameOver? 1:0];
            for(int i = 0; i < 20; i++) {
                Particle p = new Particle(getCenter().getX(), getCenter().getY(), currentColor.darker());
                getCanvas().add(p);
                animations.add(p);
            }
        } else {
            currentColor = clearedColor[(int)(3 * Math.random())];
            if(number > 0) {
                numberAsObject = new GraphicsText(number + "");
                numberAsObject.setFillColor(fontColor);
                numberAsObject.setFont(FontStyle.PLAIN, tile.getHeight() * .5);
                numberAsObject.setCenter(tile.getCenter());
                add(numberAsObject);

                for(int i = 0; i < 4; i++) {
                    Particle p = new Particle(getCenter().getX(), getCenter().getY(), currentColor.darker());
                    getCanvas().add(p);
                    animations.add(p);
                }
            }
        }
        tile.setFillColor(currentColor);
        clicked = true;
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

    public String toString() {
        return ("Tile at: " + getX() + ", " + getY() + (bomb ? " is a bomb" : " has number " + number));
    }
}

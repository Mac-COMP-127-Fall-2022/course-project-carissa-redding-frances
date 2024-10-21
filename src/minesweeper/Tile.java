package minesweeper;

import java.awt.Color;

import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;
import minesweeper.animations.AniManager;
import minesweeper.animations.Fall;
import minesweeper.animations.Particle;
import minesweeper.animations.SizeIn;
/**
 * A single tile in a Minesweeper grid
 * @author Frances McConnell
 * @author Carissa Bolante
 * @author Redding Sauter
 */
public class Tile extends GraphicsGroup {
    private final double initialSize;

    private Color[] hiddenColor = {Minesweeper.budGreen, Minesweeper.budGreen2};
    private Color[] clearedColor = {Minesweeper.brownSugar, Minesweeper.brownSugarShade1, Minesweeper.brownSugarTint1};
    private Color[] bombColor = {Minesweeper.redOrange, Minesweeper.selectiveYellow};
    private Color fontColor = Minesweeper.vividSkyBlue;
    private Color currentColor;

    private Boolean bomb = false;
    private int number = 0;
    private boolean clicked = false;
    private boolean flag = false;
    private Image flagImage = new Image("redflag.png");
    private Rectangle tile;
    private static boolean chessboard = false;
    private GraphicsText numberAsObject = new GraphicsText("");
    AniManager animations;

    /**
     * Creates a tile for a Minesweeper game
     * @param size 
     * @param animations AniManager object passed for particle effect
     */
    public Tile(double size, AniManager animations) {
        this.initialSize = size;
        this.animations = animations;
        tile = new Rectangle(0,0, size, size);
        tile.setAnchor(0, 0);
        setAnchor(0, 0);
        currentColor = hiddenColor[chessboard? 0:1];
        tile.setFillColor(currentColor);
        add(tile);
        flagImage.setMaxHeight(size);
        flagImage.setCenter(tile.getCenter().scale(tile.getScaleX()));
        chessboard = !chessboard;

    }

    /* --------------------------------- Visuals -------------------------------- */
    
    /**
     * Adds or removes a flag image and prevents user from clicking when flagged
     */
    public void setFlag() {
        if(clicked && !this.flag) {
            return;
        }
        if(!flag) {
            add(flagImage);
            animations.add(new SizeIn(flagImage));
        } else {
            animations.remove("Size In");
            remove(flagImage);
            Image fallingImage = new Image("redflag.png");
            fallingImage.setMaxHeight(getHeight());
            fallingImage.setCenter(getCenter());
            animations.add(new Fall(fallingImage, getCanvas()));
        }
        clicked = !clicked;
        flag = !flag;
    }

    /**
     * Reveals state of tile (bomb, number, or empty) 
     * @param gameOver determines whether bombs are displayed as red or orange
     */
    public void reveal(boolean gameOver) {
        int particleCount = 0;
        if(bomb) {
            currentColor = bombColor[gameOver? 1:0];
            particleCount = 20;
        } else {
            currentColor = clearedColor[(int)(3 * Math.random())];
            if(number > 0) {
                numberAsObject = new GraphicsText(number + "");
                numberAsObject.setFillColor(fontColor);
                numberAsObject.setFont(FontStyle.PLAIN, tile.getHeight() * 0.5 * tile.getScaleY());
                numberAsObject.setCenter(tile.getCenter().scale(tile.getScaleX()));
                add(numberAsObject);
                particleCount = 4;
            }
        }
        for(int i = 0; i < particleCount; i++) {
            animations.add(new Particle(getCenter().getX(), getCenter().getY(), currentColor.darker(), getCanvas()));
        }
        tile.setFillColor(currentColor);
        clicked = true;
        if(flag) {
            setFlag();
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

    public String toString() {
        return ("Tile at: " + getX() + ", " + getY() + (bomb ? " is a bomb" : " has number " + number));
    }

    public Color getColor() {
        return currentColor;
    }

    public void setSize(double size) {
        tile.setScale(size / initialSize);
        flagImage.setMaxHeight(size);
        flagImage.setCenter(tile.getCenter().scale(tile.getScaleX()));
        numberAsObject.setFontSize(size * 0.5);
        numberAsObject.setCenter(tile.getCenter().scale(tile.getScaleX()));
    }
}

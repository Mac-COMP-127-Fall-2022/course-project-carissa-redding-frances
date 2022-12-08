package minesweeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import minesweeper.animations.AniManager;
import minesweeper.animations.Delay;
import minesweeper.animations.FlyIn;
import minesweeper.animations.ScreenShake;

/**
 * Creates and orders tile objects, randomly generates bombs, and handles click behavior
 * @author Frances McConnell
 * @author Carissa Bolante
 * @author Redding Sauter
 */
public class TileGrid {
    private GraphicsGroup group = new GraphicsGroup();
    private double tileSize;
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    private int numBombs;
    private AniManager animations;

    /**
     * @param gridSize Height and width of grid (always square)
     * @param canvasSize Width of canvas window
     * @param numBombs
     * @param animations Passing TileGrid an AniManager object to add and manage animations
     */
    public TileGrid(int gridSize, double canvasSize, int numBombs, AniManager animations) {
        this.numBombs = numBombs;
        this.tileSize = canvasSize / gridSize;
        this.animations = animations;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Tile tile = new Tile(tileSize, animations);
                tile.setPosition(i * tileSize, j * tileSize);
                group.add(tile);
                tileList.add(tile);
                animations.add(new FlyIn(tile, Math.random()));
            }
        }
    }

    /* ----------------------------- MAIN BEHAVIOUR ----------------------------- */
    /**
     * Main behaviours activated when players attempt to reveal a tile.
     * 
     * @return false if the game is over, otherwise true
     */
    public boolean clickTile(Tile tile) {
        if (tile == null || tile.getClicked()) {
            return true;
        }
        tile.reveal(false);
        if (tile.getBomb()) {
            displayBombs(tile);
            return false;
        } else {
            if (tile.getNumber() == 0) {
                for (Tile neighbor : getNeighboringTiles(tile)) {
                    animations.add(new ScreenShake(group));
                    clickTile(neighbor);
                }
            }
            if (checkWin()) {
                return false;
            }
        }
        return true;
    }

    public void flagTile(Tile tile) {
        if (tile == null) {
            return;
        }
        tile.setFlag();
    }


    /**
     * When a bomb is clicked and the player loses, reveals all hidden bombs.
     */
    private void displayBombs(Tile clickedTile) {
        double delay = 0.25;
        Random rand = new Random();
        List<Tile> bombList = new ArrayList<Tile>(tileList.stream().filter((tile) -> tile.getBomb()).filter((tile) -> tile != clickedTile).toList());
        Collections.shuffle(bombList, rand);
        for (Tile tile : bombList) {
            delay += 0.5;
            animations.add(new Delay(() -> {
                tile.reveal(true);
                animations.add(new ScreenShake(group));
            }, delay));
        }
        animations.add(new ScreenShake(group));
    }

    /* ---------------------------------- LOGIC --------------------------------- */
    /**
     * Randomly assigns bombs to a number of tiles given when the grid is initialized
     * 
     * @param clickLocation of initial click, generates no bombs around this point
     */
    public void assignBombPositions(Point clickLocation) {
        Random rand = new Random();
        Tile clickedTile = getTileAt(clickLocation);
        List<Tile> copiedList = new ArrayList<>(tileList);
        Set<Tile> neighborSet = new HashSet<Tile>();
        for (Tile tile : getNeighboringTiles(clickedTile)) {
            if (tile != null) {
                neighborSet.addAll(getNeighboringTiles(tile));
            }
        }
        copiedList.removeAll(neighborSet);
        Collections.shuffle(copiedList, rand);
        for (int i = 0; i < numBombs; i++) {
            copiedList.get(i).setBomb(true);
        }

        // loops through tiles and sets appropriate number
        for (Tile tile : tileList) {
            int bombCount = 0;
            for (Tile neighbor : getNeighboringTiles(tile)) {
                if (neighbor != null) {
                    if (neighbor.getBomb()) {
                        bombCount++;
                    }
                }
            }
            tile.setNumber(bombCount);
        }
    }

    /**
     * Checks for a win state
     * 
     * @return true if the player has won
     */
    public boolean checkWin() {
        for (Tile tile : tileList) {
            if (!tile.getClicked() && !tile.getBomb()) {
                return false;
            }
        }
        return true;
    }

    /* --------------------------------- HELPERS -------------------------------- */

    /**
     * Returns a list of the four tiles neighboring a given tile in the format {north, south, west,
     * east, northeast, southeast, southwest, northwest}. Entries may be null if tile is on an edge.
     */
    public List<Tile> getNeighboringTiles(Tile tile) {
        if (tile == null) {
            return null;
        }
        Point center = tile.getCenter();
        List<Tile> neighbors = new ArrayList<>(8);
        List<Point> neighborPoints = new ArrayList<Point>(List.of(center.withY(center.getY() - tileSize), // north
            center.withY(center.getY() + tileSize), // south
            center.withX(center.getX() - tileSize), // west
            center.withX(center.getX() + tileSize), // east
            new Point(center.getX() + tileSize, center.getY() - tileSize), // northeast
            new Point(center.getX() + tileSize, center.getY() + tileSize), // southeast
            new Point(center.getX() - tileSize, center.getY() + tileSize), // southwest
            new Point(center.getX() - tileSize, center.getY() - tileSize))); // northwest

        for (Point point : neighborPoints) {
            neighbors.add(getTileAt(point));
        }
        return neighbors;
    }

    /**
     * Returns the tile at a given point. Will return null if a tile is not present.
     */
    public Tile getTileAt(Point p) {
        for (Tile tile : tileList) {
            if (tile.testHit(p.getX(), p.getY())) {
                return tile;
            }
        }
        return null;
    }

    /**
     * returns the grid's visual information
     */
    public GraphicsGroup getGroup() {
        return group;
    }

    public ArrayList<Tile> getTileList() {
        return new ArrayList<>(tileList);
    }


    /**
     * @return true if any tile has been clicked
     */
    public boolean checkClicked() {
        for (Tile tile : tileList) {
            if (tile.getClicked()) {
                return true;
            }
        }
        return false;
    }
}

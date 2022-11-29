import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;

public class TileGrid {
    private GraphicsGroup group = new GraphicsGroup();
    private int tileSize;
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    private CanvasWindow canvas;
    private int numBombs;

    public TileGrid(int gridSize, int canvasSize, CanvasWindow canvas, int numBombs) {
        this.numBombs = numBombs;
        this.tileSize = canvasSize / gridSize;
        this.canvas = canvas;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Tile tile = new Tile(tileSize);
                tile.setPosition(i * tileSize, j * tileSize);
                group.add(tile);
                tileList.add(tile);
            }
        }
    }

    /**
     * Checks for a win state, returns true if the player has won
     * 
     * @return boolean
     */
    public boolean checkWin() {
        for (Tile tile : tileList) {
            if (!tile.clicked() && !tile.getBomb()) {
                return false;
            }
        }
        return true;
    }

    // ----------------------------------MAIN BEHAVIOUR---------------------------------
    /**
     * Main behaviours activated when players attempt to reveal a tile.
     * 
     * @param tile
     * @return boolean
     */
    public boolean clickTile(Tile tile) {
        if (tile == null || tile.clicked()) {
            return true;
        }
        GraphicsText numberAsObject;
        tile.setClicked(true);

        if (tile.getBomb()) {
            tile.setFillColor(Color.RED);
            displayBombs(tile);
            return false;
        } else {
            tile.setFillColor(Color.GREEN);
            if (tile.getNumber() > 0) {
                numberAsObject = new GraphicsText(tile.getNumber() + "");
                numberAsObject.setFillColor(Color.BLUE);
                numberAsObject.setFont(FontStyle.PLAIN, tile.getHeight() * .5);
                numberAsObject.setCenter(tile.getCenter());
                canvas.add(numberAsObject);
            } else {
                for (Tile neighbor : getNeighboringTiles(tile)) {
                    clickTile(neighbor);
                }
            }
            if (checkWin()) {
                return false;
            }
        }
        return true;
    }


    /**
     * @param tile
     */
    public void flagTile(Tile tile) {
        if (tile == null || (tile.clicked() && !tile.getFlagged())) {
            return;
        }

        if (!tile.getFlagged()) {
            group.add(tile.getFlag());
            tile.getFlag().setCenter(tile.getCenter());
            tile.setFlagged(true);
            tile.setClicked(true);
        } else {
            group.remove(tile.getFlag());
            tile.setFlagged(false);
            tile.setClicked(false);
        }
    }


    /**
     * When a bomb is clicked and the player loses, reveals all uncovered bombs.
     * 
     * @param clickedTile
     */
    private void displayBombs(Tile clickedTile) {
        for (Tile tile : tileList) {
            if (tile.getBomb() && tile != clickedTile) {
                tile.setFillColor(Color.ORANGE);
                if (tile.getFlagged()) {
                    group.remove(tile.getFlag());
                }
            }
        }
    }

    // --------------------------------------LOGIC--------------------------------------
    /**
     * Randomly assigns bombs to a number of tiles given when the grid is initialized
     * 
     * @param clickLocation
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

    // -------------------------------------HELPERS-------------------------------------
    /**
     * Returns a list of the four tiles neighboring a given tile in the format {north, south, west,
     * east, northeast, southeast, southwest, northwest}. Entries may be null if tile is on an edge.
     * 
     * @param tile
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
     * 
     * @param point
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
     * 
     * @return GraphicsGroup
     */
    public GraphicsGroup getGroup() {
        return group;
    }

    /**
     * currently only for internal testing
     */
    public ArrayList<Tile> getTileList() {
        return new ArrayList<>(tileList);
    }


    /**
     * @return boolean
     */
    public boolean checkClicked() {
        for (Tile tile : tileList) {
            if (tile.clicked()) {
                return true;
            }
        }
        return false;
    }
}

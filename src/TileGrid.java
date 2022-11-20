import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TileGrid {
    private GraphicsGroup group = new GraphicsGroup();
    private int tileSize;
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    private CanvasWindow canvas;
    private int numBombs;

    public TileGrid(int gridSize, int canvasSize, CanvasWindow canvas, int numBombs) {
        this.numBombs = numBombs;
        this.tileSize = canvasSize/gridSize;
        this.canvas = canvas;

        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                Tile tile = new Tile(tileSize);
                tile.setPosition(i * tileSize, j * tileSize);
                group.add(tile);
                tileList.add(tile);
            }
        }
    }

    public void clickTile(Tile tile) {
        if(tile == null || tile.clicked()) {
            return;
        }
        GraphicsText numberAsObject;
        tile.setClicked(true);
        
        if (tile.getBomb()) {
            tile.setFillColor(Color.RED);
        } else {
            tile.setFillColor(Color.GREEN);
            if(tile.getNumber() > 0) {
                numberAsObject = new GraphicsText(tile.getNumber()+"");
                numberAsObject.setFillColor(Color.BLUE);
                numberAsObject.setFont(FontStyle.PLAIN, tile.getHeight()*.5);
                numberAsObject.setCenter(tile.getCenter());
                canvas.add(numberAsObject);
            } else {
                for(Tile neighbor : getNeighboringTiles(tile)) {
                    clickTile(neighbor);
                }
            }
        }
    }

    /*
     * Returns a list of the four tiles neighboring a given tile in the format {north, south, west, east}. Entries may be null if tile is on an edge.
     */
    public List<Tile> getNeighboringTiles(Tile tile) {
        Point center = tile.getCenter();
        List<Tile> neighbors = new ArrayList<>(8);
        List<Point> neighborPoints = new ArrayList<Point>(List.of(center.withY(center.getY() - tileSize), // north
                                                             center.withY(center.getY() + tileSize), // south
                                                             center.withX(center.getX() - tileSize), // west
                                                             center.withX(center.getX() + tileSize), // east
                                                             new Point(center.getX()+tileSize, center.getY()-tileSize), // northeast
                                                             new Point(center.getX()+tileSize, center.getY()+tileSize), // southeast
                                                             new Point(center.getX()-tileSize, center.getY()+tileSize), // southwest
                                                             new Point(center.getX()-tileSize, center.getY()-tileSize))); // northwest
                                    
        for(Point point : neighborPoints) {
            neighbors.add(getTileAt(point));
        }
        return neighbors;
    }

    /*
     * Returns the tile at a given point. Will return null if a tile is not present.
     */
    public Tile getTileAt(Point p) {
        Object tester = group.getElementAt(p);

        if(tester != null && tileList.contains(tester)) {
            return tileList.get(tileList.indexOf(tester));
        } else {
            return null;
        }
    }

    public GraphicsGroup getGroup() {
        return group;
    }
    /*
     * currently only for internal testing
     */
    public ArrayList<Tile> getTileList() {
        return new ArrayList<>(tileList);
    }

    public boolean checkClicked() {
        for (Tile tile : tileList) {
            if (tile.clicked()) {
                return true;
            }
        }
        return false;
    }

    public void assignBombPositions(Point clickLocation) {
        Random rand = new Random();
        Tile clickedTile = getTileAt(clickLocation);
        List<Tile> copiedList = new ArrayList<>(tileList);
        Set<Tile> neighborSet = new HashSet<Tile>(getNeighboringTiles(clickedTile));
        for (Tile tile : getNeighboringTiles(clickedTile)) {
            neighborSet.addAll(getNeighboringTiles(tile)); // TODO throws a bunch of error when first clicked tile is on the edge
        }                                                  // (doesn't interrupt gameplay, but may be something to catch)
        copiedList.removeAll(neighborSet);
        Collections.shuffle(copiedList, rand);
        for (int i=0; i<numBombs; i++) {
            copiedList.get(i).setBomb(true);
        }

        // loops through tiles and sets appropriate number
        for (Tile tile : tileList) {
            int bombCount = 0;
            for (Tile neighbor : getNeighboringTiles(tile)) {
                if (neighbor!=null) {
                    if (neighbor.getBomb()) {
                        bombCount++;
                    }
                }
            }
            tile.setNumber(bombCount);
        }
    }

}

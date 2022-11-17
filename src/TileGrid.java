import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TileGrid {
    private GraphicsGroup group = new GraphicsGroup();
    private int tileSize;
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    private CanvasWindow canvas;

    public TileGrid(int gridSize, int canvasSize, CanvasWindow canvas) {
        this.tileSize = canvasSize/gridSize;
        this.canvas = canvas;
        int x = 0;
        int y = 0;
        for (int i = 0; i <= gridSize*gridSize; i++) { // may refactor as nested c-style loops
            Tile tile = new Tile(tileSize);
            tile.setPosition(x,y);
            group.add(tile);
            tileList.add(tile);
            if((x/tileSize)+1 == gridSize) {
                y += tileSize;
                x = 0;
            } else {
                x+=tileSize;
            }
        }

        // loops through tiles and sets appropriate number
        for (Tile tile : tileList) {
            int bombCount = 0;
            for (Tile neighbor : getNeighboringTiles(tile)) {
                if (neighbor!=null) {
                    System.out.println("NEIGHBOR FOUND");
                    if (neighbor.getBomb()) {
                        bombCount++;
                    }
                }
            }
            tile.setNumber(bombCount);
        }

        for (Tile tile : tileList) {
            System.out.println(tile.getNumber());
        }
    }

    public void clickTile(GraphicsObject tileObject) {
        GraphicsText numberAsObject;
        for (Tile tile : tileList) {
            if (tile == tileObject){
                if (tile.getBomb()) {
                    tile.setFillColor(Color.RED);
                } else {
                    tile.setFillColor(Color.GREEN);
                    numberAsObject = new GraphicsText(tile.getNumber()+"");
                    numberAsObject.setFillColor(Color.BLUE);
                    numberAsObject.setFont(FontStyle.PLAIN, tile.getHeight()*.5);
                    numberAsObject.setCenter(tile.getCenter());
                    canvas.add(numberAsObject);
                    
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
     * currently only for internal testing TODO
     */
    public ArrayList<Tile> getTileList() {
        return new ArrayList<>(tileList);
    }
}

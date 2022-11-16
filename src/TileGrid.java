import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import java.awt.Color;
import java.util.ArrayList;

public class TileGrid {
    private GraphicsGroup group = new GraphicsGroup();
    private int tileSize;
    private ArrayList<Tile> tileList = new ArrayList<Tile>();

    public TileGrid(int gridSize, int canvasSize) {
        this.tileSize = canvasSize/gridSize;
        int x = 0;
        int y = 0;
        for (int i = 0; i <= gridSize*gridSize; i++) {
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
    }

    public void clickTile(CanvasWindow canvas, GraphicsObject tileObject) {
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

    public GraphicsGroup getGroup() {
        return group;
    }
}

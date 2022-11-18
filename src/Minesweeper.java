import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class Minesweeper {
    private CanvasWindow canvas = new CanvasWindow("Minesweeper", 600, 600);
    private TileGrid grid;

    public Minesweeper() {
        int gridSize = 10;
        int numBombs = 15;
        grid = new TileGrid(gridSize, 600, canvas, numBombs);
        canvas.add(grid.getGroup());
        canvas.onClick(e -> {
            GraphicsObject object = canvas.getElementAt(e.getPosition());  // use get tile at
            if (object!=null){
                if (object.getClass()==Tile.class){
                    if (grid.checkClicked()) {
                        grid.clickTile(object);
                    } else {
                        grid.assignBombPositions(e.getPosition());
                        grid.clickTile(object);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        new Minesweeper();
    }
}

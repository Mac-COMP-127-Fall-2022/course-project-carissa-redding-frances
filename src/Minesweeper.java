import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class Minesweeper {
    private CanvasWindow canvas = new CanvasWindow("Minesweeper", 600, 600);
    private TileGrid grid = new TileGrid(10, 600, canvas);

    public Minesweeper() {
        canvas.add(grid.getGroup());
        canvas.onClick(e -> {
            GraphicsObject object = canvas.getElementAt(e.getPosition());
            if (object!=null){
                if (object.getClass()==Tile.class){
                    grid.clickTile(object);
                }
            }
        });
    }

    public static void main(String[] args) {
        new Minesweeper();
    }
}

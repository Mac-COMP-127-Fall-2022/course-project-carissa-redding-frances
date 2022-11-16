import edu.macalester.graphics.CanvasWindow;

public class Minesweeper {
    private CanvasWindow canvas = new CanvasWindow("Minesweeper", 600, 600);
    private TileGrid grid = new TileGrid(10, 600);

    public Minesweeper() {
        canvas.add(grid.getGroup());
    }

    public static void main(String[] args) {
        new Minesweeper();
    }
}

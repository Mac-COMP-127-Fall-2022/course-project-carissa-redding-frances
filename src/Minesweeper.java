import edu.macalester.graphics.CanvasWindow;

public class Minesweeper {
    private CanvasWindow canvas = new CanvasWindow("Minesweeper", 600, 600);
    private TileGrid grid;

    public Minesweeper() {
        int gridSize = 10;
        int numBombs = 15;
        grid = new TileGrid(gridSize, 600, canvas, numBombs);
        canvas.add(grid.getGroup());
        
        canvas.onClick(e -> {
            if(!grid.checkClicked()) {
                grid.assignBombPositions(e.getPosition());
            }
            grid.clickTile(grid.getTileAt(e.getPosition()));
        });
    }

    public static void main(String[] args) {
        new Minesweeper();
    }
}

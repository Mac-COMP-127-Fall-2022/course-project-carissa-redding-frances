import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.ModifierKey;

public class Minesweeper {
    private int windowSize = 600;
    private CanvasWindow canvas = new CanvasWindow("Minesweeper", windowSize, windowSize);
    private TileGrid grid;

    public Minesweeper() {
        int gridSize = 10;
        int numBombs = 15;
        grid = new TileGrid(gridSize, windowSize, canvas, numBombs);
        grid.getGroup().setPosition(0,0);
        canvas.add(grid.getGroup());
        
        canvas.onClick(e -> {
            if (e.getModifiers().contains(ModifierKey.SHIFT)) {
                grid.flagTile(grid.getTileAt(e.getPosition()));
            } else {
                if(!grid.checkClicked()) {
                    grid.assignBombPositions(e.getPosition());
                }
                grid.clickTile(grid.getTileAt(e.getPosition()));
            }
        });
    }

    public static void main(String[] args) {
        new Minesweeper();
    }
}

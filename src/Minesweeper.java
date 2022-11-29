import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.events.ModifierKey;
import edu.macalester.graphics.ui.Button;

public class Minesweeper {
    private int windowSize = 600;
    private CanvasWindow canvas = new CanvasWindow("Minesweeper", windowSize, windowSize);
    private TileGrid grid;
    private boolean running = false;
    private int gridSize;
    private int numBombs;
    private GraphicsText displayedText = new GraphicsText();

    // -------------------------------------RUNTIME-------------------------------------
    public Minesweeper() {
        chooseMode();
    }

    public static void main(String[] args) {
        new Minesweeper();
    }

    // ----------------------------------USER INTERFACE---------------------------------
    private void chooseMode() {
        displayedText.setText("Minesweeper");
        formatText();
        Button easyButton = new Button("Easy");
        easyButton.setCenter(windowSize * .25, windowSize * .7);
        Button mediumButton = new Button("Medium");
        mediumButton.setCenter(windowSize * .5, windowSize * .7);
        Button hardButton = new Button("Hard");
        hardButton.setCenter(windowSize * .75, windowSize * .7);
        canvas.add(easyButton);
        canvas.add(mediumButton);
        canvas.add(hardButton);

        easyButton.onClick(() -> {
            gridSize = 10;
            numBombs = 15;
            playGame();
        });

        mediumButton.onClick(() -> {
            gridSize = 15;
            numBombs = 30;
            playGame();
        });

        hardButton.onClick(() -> {
            gridSize = 20;
            numBombs = 99;
            playGame();
        });
    }

    private void endGameMessage(boolean win) {
        displayedText.setText("You " + (win ? "Win!" : "Lose!"));
        formatText();
        Button exit = new Button("Exit");
        Button replay = new Button("Play Again");

        exit.setCenter(windowSize * .4, windowSize * .7);
        replay.setCenter(windowSize * .6, windowSize * .7);

        canvas.add(exit);
        canvas.add(replay);

        exit.onClick(() -> {
            canvas.closeWindow();
        });
        replay.onClick(() -> {
            canvas.removeAll();
            chooseMode();
        });
    }

    private void formatText() {
        displayedText.setFont(FontStyle.BOLD, windowSize * 0.1);
        displayedText.setWrappingWidth(windowSize - 50);
        displayedText.setCenter(canvas.getCenter());
        canvas.add(displayedText);
    }

    // -------------------------------------GAMEPLAY------------------------------------
    public void playGame() {
        canvas.removeAll();
        running = true;
        grid = new TileGrid(gridSize, windowSize, canvas, numBombs);
        grid.getGroup().setPosition(0, 0);
        canvas.add(grid.getGroup());

        canvas.onClick(e -> {
            if (running) {
                if (e.getModifiers().contains(ModifierKey.SHIFT)) {
                    grid.flagTile(grid.getTileAt(e.getPosition()));
                } else {
                    if (!grid.checkClicked()) {
                        grid.assignBombPositions(e.getPosition());
                    }
                    if (!grid.clickTile(grid.getTileAt(e.getPosition()))) {
                        running = false;
                        endGameMessage(grid.checkWin());
                    }
                }
            }
        });
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    // -------------------------------------HELPERS-------------------------------------

    // TODO: put new color palette here
}

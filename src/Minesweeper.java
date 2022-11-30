import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.events.ModifierKey;
import edu.macalester.graphics.ui.Button;
import java.awt.Color;

public class Minesweeper {
    private int windowSize = 600;
    private CanvasWindow canvas = new CanvasWindow("Minesweeper", windowSize, windowSize+80);
    private TileGrid grid;
    private boolean running = false;
    private int gridSize;
    private int numBombs;
    private GraphicsText displayedText = new GraphicsText();
    GraphicsText numFlags = new GraphicsText();

    // -------------------------------------RUNTIME-------------------------------------
    public Minesweeper() {
        canvas.setBackground(green);
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
            canvas.setBackground(green);
            chooseMode();
        });
    }

    private void formatText() {
        displayedText.setFont(FontStyle.BOLD, windowSize * 0.1);
        displayedText.setWrappingWidth(windowSize - 50);
        displayedText.setCenter(canvas.getCenter());
        canvas.add(displayedText);
    }

    private void createFlagCounter() {
        numFlags.setText(numBombs+"");
        numFlags.setFillColor(grey);
        numFlags.setCenter(windowSize * 0.5, windowSize + 50);
        numFlags.setFont(FontStyle.BOLD, 50);
        canvas.add(numFlags);
        Image exampleFlag = new Image("images/redflag.png");
        exampleFlag.setMaxHeight(50);
        exampleFlag.setCenter(numFlags.getCenter().getX() - exampleFlag.getWidth(), numFlags.getCenter().getY());
        canvas.add(exampleFlag);
    }

    private void updateFlagCounter() {
        numFlags.setText(numBombs - grid.getTileList().stream().filter((tile)-> tile.getFlagged()).count() + "");
    }

    // -------------------------------------GAMEPLAY------------------------------------
    public void playGame() {
        canvas.removeAll();
        canvas.setBackground(blue);
        createFlagCounter();
        running = true;
        grid = new TileGrid(gridSize, windowSize, canvas, numBombs);
        grid.getGroup().setPosition(0, 0);
        canvas.add(grid.getGroup());

        canvas.onClick(e -> {
            if (running) {
                if (!grid.checkClicked()) { // initial click
                    grid.assignBombPositions(e.getPosition());
                    grid.clickTile(grid.getTileAt(e.getPosition()));
                } else if (e.getModifiers().contains(ModifierKey.SHIFT)) {
                    grid.flagTile(grid.getTileAt(e.getPosition()));
                } else {
                    if (!grid.clickTile(grid.getTileAt(e.getPosition()))) { // This if statement calls the main game behavior
                        running = false;
                        endGameMessage(grid.checkWin());
                    }
                }
                updateFlagCounter();
            }
        });
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    // -------------------------------------HELPERS-------------------------------------
    public static Color green = new Color(108,120,55);
    public static Color grey = new Color(201,201,201);
    public static Color red = new Color(142,32,32);
    public static Color orange = new Color(196,99,64);
    public static Color blue = new Color(27,29,132);
    public static Color brown = new Color(140,103,79);
    public static Color backgroundColor = new Color(52,115,196);
}

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.events.ModifierKey;
import edu.macalester.graphics.ui.Button;
import java.awt.Color;
import java.util.Scanner;

public class Minesweeper {
    private int windowSize = 600;
    private CanvasWindow canvas = new CanvasWindow("Minesweeper", windowSize, windowSize + 80);
    private TileGrid grid;
    private AniManager animations;
    private boolean running = false;
    private int gridSize;
    private int numBombs;
    private GraphicsText displayedText = new GraphicsText();
    private GraphicsText numFlags = new GraphicsText();

    private Color backgroundColor = budGreen;
    private Color textColor = blackCoffee;
    private Color barColor = blueSapphire;

    /* --------------------------------- RUNTIME -------------------------------- */
    public Minesweeper() {
        canvas.setBackground(backgroundColor);
        chooseMode();
        animations = new AniManager(canvas);

        canvas.onClick(e -> {
            if (running) {
                if (!grid.checkClicked()) { // initial click
                    animations.clearQueue();
                    grid.assignBombPositions(e.getPosition());
                    grid.clickTile(grid.getTileAt(e.getPosition()));
                } else if (e.getModifiers().contains(ModifierKey.SHIFT)) {
                    grid.flagTile(grid.getTileAt(e.getPosition()));
                } else {
                    if (!grid.clickTile(grid.getTileAt(e.getPosition()))) { // This if statement calls the main game
                                                                            // behavior
                        running = false;
                        endGameMessage(grid.checkWin());
                    }
                }
                updateFlagCounter();
            }
        });
    }

    public static void main(String[] args) {
        new Minesweeper();
    }

    /* ----------------------------- USER INTERFACE ----------------------------- */
    private void chooseMode() {
        displayedText.setText("Minesweeper");
        formatText();

        Button easyButton = new Button("Easy");
        Button mediumButton = new Button("Medium");
        Button hardButton = new Button("Hard");
        Button customButton = new Button("Custom");

        easyButton.setCenter(windowSize * .25, windowSize * .7);
        mediumButton.setCenter(windowSize * .5, windowSize * .7);
        hardButton.setCenter(windowSize * .75, windowSize * .7);
        customButton.setCenter(windowSize * .5, windowSize * .8);

        canvas.add(easyButton);
        canvas.add(mediumButton);
        canvas.add(hardButton);
        canvas.add(customButton);

        easyButton.onClick(() -> {
            gridSize = 11;
            numBombs = 15;
            playGame();
        });

        mediumButton.onClick(() -> {
            gridSize = 15;
            numBombs = 30;
            playGame();
        });

        hardButton.onClick(() -> {
            gridSize = 21;
            numBombs = 99;
            playGame();
        });

        customButton.onClick(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose grid size: ");
            gridSize = scanner.nextInt();

            System.out.println("Choose number of bombs: ");
            numBombs = scanner.nextInt();

            playGame();
        });
    }

    private void endGameMessage(boolean win) {
        displayedText.setText("You " + (win ? "Win!" : "Lose!"));
        if(win) {
            Color[] randomColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};
            for(int i = 0; i < 150; i++) {
                Particle p = new Particle(canvas.getCenter().getX(), canvas.getCenter().getY() - i * 2, randomColors[(int)(Math.random() * randomColors.length)]);
                animations.add(new Delay(() -> {
                    animations.add(p);
                        animations.add(new ScreenShake(grid.getGroup()));
                    canvas.add(p);
                }, 0.5 * (int)(i * 0.05)));
            }
        }
        formatText();
        Button exit = new Button("Exit");
        Button replay = new Button("Play Again");

        exit.setCenter(windowSize * .4, windowSize * .7);
        replay.setCenter(windowSize * .6, windowSize * .7);

        canvas.add(exit);
        canvas.add(replay);

        exit.onClick(() -> {
            animations.clearQueue();
            canvas.closeWindow();
        });
        replay.onClick(() -> {
            animations.clearQueue();
            canvas.removeAll();
            canvas.setBackground(backgroundColor);
            chooseMode();
        });
    }

    private void formatText() {
        displayedText.setFont(FontStyle.BOLD, windowSize * 0.1);
        displayedText.setWrappingWidth(windowSize - 50);
        displayedText.setCenter(canvas.getCenter());
        displayedText.setFillColor(textColor);
        canvas.add(displayedText);
    }

    private void createFlagCounter() {
        numFlags.setText(numBombs + "");
        numFlags.setFillColor(textColor);
        numFlags.setCenter(windowSize * 0.5, windowSize + 50);
        numFlags.setFont(FontStyle.BOLD, 50);
        
        canvas.add(numFlags);
        Image exampleFlag = new Image("images/redflag.png");
        exampleFlag.setMaxHeight(50);
        exampleFlag.setCenter(numFlags.getCenter().getX() - exampleFlag.getWidth(), numFlags.getCenter().getY());
        animations.add(new FlyIn(exampleFlag, 0));
        animations.add(new FlyIn(numFlags, 0));
        canvas.add(exampleFlag);
    }

    private void updateFlagCounter() {
        numFlags.setText(numBombs - grid.getTileList().stream().filter((tile) -> tile.getFlag()).count() + "");
    }

    /* -------------------------------- GAMEPLAY -------------------------------- */
    public void playGame() {
        canvas.removeAll();
        canvas.setBackground(barColor);
        createFlagCounter();
        running = true;
        grid = new TileGrid(gridSize, windowSize, numBombs, animations);
        grid.getGroup().setPosition(0, 0);
        canvas.add(grid.getGroup());
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /* --------------------------------- HELPERS -------------------------------- */

    public static Color budGreen = Color.decode("#6DA34D");
    public static Color budGreen2 = Color.decode("#83B766");
    public static Color selectiveYellow = Color.decode("#FFBA08");
    public static Color blackCoffee = Color.decode("#3E363F");
    public static Color redOrange = Color.decode("#FF3F00");
    public static Color blueSapphire = Color.decode("#2D5D7B");
    public static Color imperialRed = Color.decode("#F71735");
    public static Color forestGreen = Color.decode("#1E3F20");
    public static Color brownSugar = Color.decode("#955E42");
    public static Color brownSugarTint1 = Color.decode("#A46648");
    public static Color brownSugarShade1 = Color.decode("#8A573D");
    public static Color vividSkyBlue = Color.decode("#42CAFD");
}

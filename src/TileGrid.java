import edu.macalester.graphics.GraphicsGroup;

public class TileGrid {
    private GraphicsGroup group = new GraphicsGroup();
    private int tileSize;

    public TileGrid(int gridSize, int canvasSize) {
        this.tileSize = canvasSize/gridSize;
        int x = 0;
        int y = 0;
        for (int i = 0; i < gridSize*gridSize; i++) {
            Tile tile = new Tile(tileSize);
            tile.setPosition(x,y);
            group.add(tile);
            if(x/tileSize == gridSize) {
                y += tileSize;
                x = 0;
            } else {
                x+=tileSize;
            }
        }
    }

    public GraphicsGroup getGroup() {
        return group;
    }

    public static void main(String[] args) {
        System.out.println("hello");
        new TileGrid(12, 600);
    }
}

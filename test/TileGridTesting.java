import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.channels.SelectionKey;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.macalester.graphics.CanvasWindow;

import org.junit.jupiter.api.Test;

public class TileGridTesting {

    @Test
    void getNeighboringTilesTest() {
        CanvasWindow canvas = new CanvasWindow("stinky", 600, 600);
        int nullCount = 0;
        TileGrid grid = new TileGrid(10, 600, canvas);
        canvas.add(grid.getGroup());
        for(Tile tile : grid.getTileList()) {
            List<Tile> neighbors = grid.getNeighboringTiles(tile);
            
            for(Tile smile : neighbors) {
                System.out.println(smile);
                if(smile == null) {
                    nullCount ++;
                } else {
                    assertTrue(grid.getTileList().contains(smile));
                }
            }
        }
        List<Tile> tileList = grid.getTileList();
        Tile testTile = tileList.get(11);
        assertEquals(List.of(tileList.get(1), tileList.get(21), tileList.get(10), tileList.get(12)), grid.getNeighboringTiles(testTile));

        //assertEquals(40, nullCount); //TODO: how many nulls? why isn't this working? I think there's a weird tile somewhere
    }


    
}

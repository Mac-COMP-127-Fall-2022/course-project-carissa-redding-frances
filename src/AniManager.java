import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;

public class AniManager {
    private List<Animation> animations = new ArrayList<>();
    
    public AniManager(CanvasWindow canvas) {
        canvas.animate((dt)-> {
            animations.removeAll(animations.stream().filter((animation)-> !animation.step(dt)).toList());
        });
    }
    public void add(Animation a) {
        animations.add(a);
    }
}

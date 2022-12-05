// Authors: Carissa Bolante, Redding Sauter, Frances McConnell
// A manager for animations

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;

public class AniManager {
    private List<Animation> animations = new ArrayList<>();
    
    public AniManager(CanvasWindow canvas) {
        canvas.animate((dt)-> {
            List<Animation> copy = new ArrayList<Animation>(animations);
            animations.removeAll(copy.stream().filter((animation)-> !animation.step(dt)).toList());
        });
    }
    public void add(Animation a) {
        animations.add(a);
    }
 
    public int getQueueSize() {
        return animations.size();
    }

    public void clearQueue() {
        animations.stream().forEach((animation) -> animation.forceQuit());
        animations.clear();
    }

    /**
     * @return a list of the names of all the active animations
     */
    public List<String> getQueue() {
        return animations.stream().map((animation) -> animation.toString()).toList();
    }
}

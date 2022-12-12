package minesweeper.animations;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;

/**
 * Manages (adds, prioritizes, runs, removes) animations
 * 
 * @author Redding Sauter
 * @author Carissa Bolante
 * @author Frances McConnell
 */
public class AniManager {
    private List<Animation> animations = new ArrayList<>();
    private List<Runnable> sizers = new ArrayList<>();
    private double windowSize;

    public AniManager(CanvasWindow canvas) {
        windowSize = canvas.getWidth();

        canvas.animate((dt) -> {
            double previousSize = windowSize;
            windowSize = Math.min(canvas.getWidth(), canvas.getHeight() - 80);
            if (windowSize != previousSize) {
                runSizers();
            }

            List<Animation> copy = new ArrayList<Animation>(animations);
            animations.removeAll(copy.stream().filter((animation) -> !animation.step(dt)).toList());
        });
    }

    public void add(Animation a) {
        animations.add(a);
    }

    /**
     * Used for adding scalables to be run continuously
     * 
     * @param r
     */
    public void addSizer(Runnable r) {
        sizers.add(r);
    }

    public int getQueueSize() {
        return animations.size();
    }

    /**
     * Runs the force quit behaviour for all animations in queue and removes them
     */
    public void clearQueue() {
        animations.stream().forEach((animation) -> animation.forceQuit());
        animations.clear();
    }

    /**
     * Removes all animations with the given name
     * 
     * @param name formatted with capitals and spaces
     * @see getQueue returns the same formatting
     */
    public void remove(String name) {
        List<Animation> copy = new ArrayList<Animation>(animations);
        for (Animation animation : copy) {
            if (animation.toString().equals(name)) {
                animation.forceQuit();
                animations.remove(animation);
            }
        }
    }

    public void runSizers() {
        sizers.stream().forEach((r) -> r.run());
    }

    /**
     * @return a list of the names of all the active animations
     */
    public List<String> getQueue() {
        return animations.stream().map((animation) -> animation.toString()).toList();
    }

    public double getWindowSize() {
        return windowSize;
    }
}

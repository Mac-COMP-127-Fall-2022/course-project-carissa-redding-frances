package minesweeper.animations;

import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

/**
 * Eases in a given image, then deletes it
 * @author Redding Sauter
 * Acknowledgements: easings.net
 */
public class SizeIn implements Animation {
    private static final double MAX_RUNTIME = 0.3;
    private double runtime = 0;

    private final Image context;
    private final Point center;
    private final double initialSize, finalSize;

    public SizeIn(Image context) {
        this.context = context;
        center = context.getCenter();
        finalSize = context.getHeight();
        initialSize = context.getHeight() * 2;
        
        context.setMaxHeight(initialSize);
        context.setCenter(center);
    }

    @Override
    public boolean step(double dt) {
        runtime += dt;
        double stepSize = (initialSize - finalSize) * easeOutBack(runtime / MAX_RUNTIME);

        context.setMaxHeight(initialSize - stepSize);
        context.setCenter(center);

        if (runtime > MAX_RUNTIME) {
            context.setMaxHeight(finalSize);
            context.setCenter(center);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void forceQuit() {
        context.setMaxHeight(finalSize);
        context.setCenter(center);
    }

    private double easeOutBack(double x) {
        double c1 = 1.70158;
        double c3 = c1 + 1;
        
        return 1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2);
    }

    @Override
    public String toString() {
        return "Size In";
    }
}

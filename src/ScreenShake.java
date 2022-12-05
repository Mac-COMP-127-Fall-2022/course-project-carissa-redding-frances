// Authors: Carissa Bolante, Redding Sauter, Frances McConnell
// An animation that vigorously shakes a given object for half a second

import edu.macalester.graphics.GraphicsObject;

public class ScreenShake implements Animation {
    private double runtime = 0;
    private static final double MAX_RUNTIME = .5;

    private final GraphicsObject context;

    public ScreenShake(GraphicsObject context) {
        this.context = context;
    }

    /**
     * One frame of animation
     * @param dt passed from canvas.animate() 
     * @return false if animation should end
     */
    @Override
    public boolean step(double dt) {
        runtime += dt;
        if(runtime > MAX_RUNTIME) {
            context.setRotation(0);
            return false;
        } else {
            context.setRotation(Math.random() * 2 - 1);
            return true;
        }
    }

    @Override
    public void forceQuit() {
        context.setRotation(0);
    }

    @Override
    public String toString() {
        return "Screen Shake";
    }
}

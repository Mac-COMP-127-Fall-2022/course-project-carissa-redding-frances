import edu.macalester.graphics.GraphicsObject;

public class ScreenShake implements Animation {
    private double runtime = 0;
    private static final double MAX_RUNTIME = .5;

    private final GraphicsObject shaker;

    public ScreenShake(GraphicsObject shaker) {
        this.shaker = shaker;
    }

    @Override
    public boolean step(double dt) {
        runtime += dt;
        if(runtime > MAX_RUNTIME) {
            System.out.println("removed");
            shaker.setRotation(0);
            return false;
        } else {
            System.out.println("runtime: " + runtime);
            shaker.setRotation(Math.random() * 30 - 15);
            return true;
        }
        
    }
    
}

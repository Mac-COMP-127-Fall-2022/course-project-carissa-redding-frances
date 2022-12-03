import edu.macalester.graphics.GraphicsObject;

public class FlyIn implements Animation {
    private final double MAX_RUNTIME = 0.5;
    private double runtime = 0;

    private final double finalPos, initialPos;
    private final GraphicsObject context;
    private final double delay;


    public FlyIn(GraphicsObject context, double delay) {
        this.context = context;
        this.delay = delay;
        finalPos = context.getY();
        initialPos = context.getY() - 600;
        context.setY(initialPos);
    }
    @Override
    public boolean step(double dt) {
        runtime += dt;
        double stepSize = 600 / (MAX_RUNTIME / dt);
        context.moveBy(0, (runtime < delay) ? 0 : stepSize);

        if(context.getY() >= finalPos) {
            context.setY(finalPos);
            return false;
        } else {
            return true;
        }
    }
    
}

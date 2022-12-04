import edu.macalester.graphics.GraphicsObject;

public class FlyIn implements Animation {
    private final double MAX_RUNTIME = 4;
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
        double stepSize = initialPos / (MAX_RUNTIME / dt);
        stepSize = 600 * easeOutElastic((runtime - delay) / MAX_RUNTIME);
        if(runtime > delay) {
            context.setPosition(context.getX(), stepSize + initialPos);
        }
        

        if(runtime > MAX_RUNTIME + 0.1) {
            context.setY(finalPos);
            return false;
        } else {
            return true;
        }
    }


    public double easeOutElastic(double x) {
        double c4 = (2 * Math.PI) / 3;
        
        return x == 0
          ? 0
          : x == 1
          ? 1
          : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1;
    }
    // uses a math function from:
    // https://easings.net/#easeOutElastic
    @Override
    public void forceQuit() {
        context.setY(finalPos);
        
    }
}

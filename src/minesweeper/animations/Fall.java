package minesweeper.animations;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
/**
 * Makes a given obect spin and fall off the screen
 * @author Redding Sauter
 * @see Particle.java for similar implementation
 */
public class Fall implements Animation {

    private final GraphicsObject context;
    private final CanvasWindow canvas;
    private final double canvasSize;
    private final double gravity;

    private double dx, dy;

    public Fall(GraphicsObject context, CanvasWindow canvas) {
        this.context = context;
        this.canvas = canvas;
        canvasSize = Math.min(canvas.getWidth(), canvas.getHeight() - 80);
        gravity = canvasSize * 0.0011667;

        dx = Math.random() * canvasSize / 30 - canvasSize / 60;
        dy = Math.random() * canvasSize / 60 * -1;

        if(context.getCanvas() == null) {
            canvas.add(context);
        }
    }

    @Override
    public boolean step(double dt) {
        context.moveBy(dx, dy);
        context.rotateBy(10);
        dy +=  gravity;
        if(context.getY() < canvas.getHeight()) {
            return true;
        } else {
            canvas.remove(context);
            return false;
        }
    }

    @Override
    public void forceQuit() {
        canvas.remove(context);
        
    }
    
}

package minesweeper.animations;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class Fall implements Animation {

    private final GraphicsObject context;
    private final CanvasWindow canvas;

    private double dx, dy;

    public Fall(GraphicsObject context, CanvasWindow canvas) {
        this.context = context;
        this.canvas = canvas;

        dx = Math.random() * 20 - 10;
        dy = Math.random() * -10;

        if(context.getCanvas() == null) {
            canvas.add(context);
        }
    }

    @Override
    public boolean step(double dt) {
        context.moveBy(dx, dy);
        context.rotateBy(10);
        dy +=  0.7;
        if(context.getY() < 680) {
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

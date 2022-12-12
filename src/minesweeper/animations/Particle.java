package minesweeper.animations;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;
/**
 * A small rectangle that experiences gravity
 * @author Redding Sauter
 */
public class Particle extends Rectangle implements Animation {
    private static double width = 7, height = 7;

    private final CanvasWindow canvas;
    private final double canvasSize;
    private final double gravity;

    private double dx, dy;

    public Particle(double x, double y, Color color, CanvasWindow canvas) {
        super(x, y, width, height);
        this.canvas = canvas;
        canvasSize = Math.min(canvas.getWidth(), canvas.getHeight() - 80);
        gravity = canvasSize * 0.0011667;

        setScale(canvasSize / 600);

        dx = Math.random() * canvasSize / 30 - canvasSize / 60;
        dy = Math.random() * canvasSize / 60 * -1;
        setStrokeWidth(0);
        setFillColor(color);
        setRotation(Math.random() * 360);
        canvas.add(this);
    }

    /**
     * One frame of animation
     * @param dt passed from canvas.animate() 
     * @return false if animation should end
     */
    @Override
    public boolean step(double dt) {
        moveBy(dx, dy);
        dy +=  gravity;
        if(getY() < canvas.getHeight()) {
            return true;
        } else {
            canvas.remove(this);
            return false;
        }
    }

    @Override
    public void forceQuit() {
        canvas.remove(this);
    }

    @Override
    public String toString() {
        return "Particle";
    }
}

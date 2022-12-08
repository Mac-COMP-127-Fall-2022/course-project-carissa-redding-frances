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

    private double dx, dy;

    public Particle(double x, double y, Color color, CanvasWindow canvas) {
        super(x, y, width, height);
        this.canvas = canvas;
        dx = Math.random() * 20 - 10;
        dy = Math.random() * -10;
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
        dy +=  0.7;
        if(getY() < 680) {
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

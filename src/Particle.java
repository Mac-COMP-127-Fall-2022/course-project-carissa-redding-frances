// Authors: Carissa Bolante, Redding Sauter, Frances McConnell
// An particle that experiences gravity

import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class Particle extends Rectangle implements Animation {
    private static double width = 7, height = 7;

    private double dx, dy;

    public Particle(double x, double y, Color color) {
        super(x, y, width, height);
        dx = Math.random() * 20 - 10;
        dy = Math.random() * -10;
        setStrokeWidth(0);
        setFillColor(color);
        setRotation(Math.random() * 360);
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
        return getY() < 680;
    }

    @Override
    public void forceQuit() {
        
    }

    @Override
    public String toString() {
        return "Particle";
    }
}

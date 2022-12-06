package minesweeper.animations;


/**
 * Authors: Carissa Bolante, Redding Sauter, Frances McConnell
 * An interface for animations
 */
public interface Animation {
    /**
     * One frame of animation
     * @param dt passed from canvas.animate() 
     * @return false if animation should end
     */
    boolean step(double dt);
    
    void forceQuit();
    
}

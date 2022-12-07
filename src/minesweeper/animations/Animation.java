package minesweeper.animations;

/**
 * An interface for animations
 * @author Redding Sauter
 * @author Carissa Bolante
 * @author Frances McConnell
 */
public interface Animation {
    
    /**
     * One frame of animation
     * @param dt passed from canvas.animate() 
     * @return false if animation should end
     */
    boolean step(double dt);
    

    /**
     * Any behaviours an animation should perform before it is deleted mid run
     */
    void forceQuit();
    
}

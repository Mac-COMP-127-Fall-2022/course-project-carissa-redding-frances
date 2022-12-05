// Authors: Carissa Bolante, Redding Sauter, Frances McConnell
// An animation that delays other runnables

public class Delay implements Animation {
    private double timer; 

    private final Runnable action;
    private final double waitTime;
    public Delay(Runnable action, double waitTime) {
        this.action = action;
        this.waitTime = waitTime;
    }

    /**
     * One frame of animation
     * @param dt passed from canvas.animate() 
     * @return false if animation should end
     */
    @Override
    public boolean step(double dt) {
        timer += dt;
        if(timer > waitTime) {
            action.run();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void forceQuit() {
        
    }

    @Override
    public String toString() {
        return "Delay";
    }
}

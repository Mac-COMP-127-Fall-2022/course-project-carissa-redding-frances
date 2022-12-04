public class Delay implements Animation {
    private double timer; 

    private final Runnable action;
    private final double waitTime;
    public Delay(Runnable action, double waitTime) {
        this.action = action;
        this.waitTime = waitTime;
    }

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
        // TODO Auto-generated method stub
    }

    @Override
    public String toString() {
        return "Delay";
    }
}

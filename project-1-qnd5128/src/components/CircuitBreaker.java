package components;

/**
 * This is the CircuitBreaker class.
 * Connects to PowerSource
 *
 * File: CircuitBreaker.java
 * Author: Nhu Quynh Duong, qnd5128
 */
public class CircuitBreaker extends Component {
    protected int limit;
    private boolean isOn = false;
    private boolean engaged;

    /**
     * creates a new CircuitBreaker and instantiate class variables
     * @param breaker the name
     * @param root the source
     * @param i rating
     */
    public CircuitBreaker(String breaker, Component root, int i) {
        super(breaker, root);
        Reporter.report(this, Reporter.Msg.CREATING);
        Reporter.report(this.getSource(), this, Reporter.Msg.ATTACHING);
        this.getSource().addLoad(this);
        this.limit = i;
    }

    /**
     * the source is powered if it already engaged
     * the source changeDraw to the rating if it is engaged and turned on
     */
    @Override
    public void engage() {
        if (!engaged) {
            engaged = true;
            Reporter.report(this, Reporter.Msg.ENGAGING);
            if (isOn) {
                this.engageLoads();
            }
        }
    }


    /**
     * turn on if it is not already turned on
     * change source rating to currentDraw
     */
    public void turnOn() {
        if (!isOn) {
            isOn = true;
            Reporter.report(this, Reporter.Msg.SWITCHING_ON);
            if (isEngage()) {
                this.engageLoads();
            }
        }
    }

    /**
     * turn off if it is not already turned off
     * draw power from the source if it is engaged
     */
    public void turnOff () {
        if (isOn) {
            isOn = false;
            Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
            if (isEngage()) {
                this.getSource().changeDraw(-this.getDraw());
                this.setDraw(0);
                this.disengageLoads();
            }
        }

    }

    /**
     *
      * @param delta
     */
    public void changeDraw ( int delta){
        if (!this.isSwitchOn()) return;
        int newLoad = this.getDraw() + delta;
        if (newLoad <= limit) {
            super.changeDraw(delta);
            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
            this.getSource().changeDraw(delta);
        } else {
            Reporter.report(this, Reporter.Msg.BLOWN, this.getDraw());
            turnOff();
        }
    }

    /**
     * the source is powered off if it is already disengaged
     * the source draws power if it is turned on
     */
    @Override
    public void disengage () {
        if (engaged) {
            engaged = false;
            Reporter.report(this, Reporter.Msg.DISENGAGING);
            if (isOn) {
                this.disengageLoads();
            }
        }
    }

    /**
     * turn on and off
     */
    @Override
    public void toggle(){
        if(!isOn){
            turnOn();
        }
        else {
            turnOff();
        }
    }

    /**
     * whether it is engaged
     * @return true if it is engaged
     */
    public boolean isEngage () {
        return engaged;
    }


    /**
     * whether it is on
     * @return true if it is on
     */
    public boolean isSwitchOn () {
        return isOn;
    }

    /**
     * Get the rating
     * @return the rating
     */
    public int getLimit () {
        return limit;
    }
}





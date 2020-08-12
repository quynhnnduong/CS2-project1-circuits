package components;

/**
 * This is the Appliance class.
 * Connects to the Outlets
 *
 * File: Appliance.java
 * Author: Nhu Quynh Duong, qnd5128
 */
public class Appliance extends Component {
    private boolean isOn = false;
    private boolean engaged;
    private int limit;
    private int currentDraw;

    /**
     * creates a new Appliance and instantiate class variables
     * @param name the name of appliance
     * @param a the source
     * @param i rating
     */
    public Appliance(String name, Component a, int i) {
        super(name, a);
        Reporter.report(this, Reporter.Msg.CREATING);
        Reporter.report(this.getSource(), this, Reporter.Msg.ATTACHING);
        this.getSource().addLoad(this);
        super.setDraw(i);
        limit = i;
        currentDraw = 0;
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
                this.getSource().changeDraw(limit);
            }
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
                this.getSource().changeDraw(-limit);
            }
        }
    }

    /**
     * turn on if it is not already turned on
     * change source rating to currentDraw if it is engaged
     */
    public void turnOn () {
        if (!isOn) {
            isOn = true;
            Reporter.report(this, Reporter.Msg.SWITCHING_ON);
            if (isEngage()) {
                currentDraw = limit;
                this.getSource().changeDraw(currentDraw);
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
                currentDraw = 0;
                this.getSource().changeDraw(-limit);
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
    public int getRating () {
        return getDraw();
    }
}

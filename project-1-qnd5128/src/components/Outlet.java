package components;

/**
 * This is the Outlet class.
 * Connects to the CircuitBreakers
 *
 * File: Outlet.java
 * Author: Nhu Quynh Duong, qnd5128
 */
public class Outlet extends Component {

    /**
     * creates a new Outlet and instantiate class variables
     * @param outlet the name of outlet
     * @param breaker the source
     */
    public Outlet(String outlet, CircuitBreaker breaker) {
        super(outlet, breaker);
        Reporter.report(this, Reporter.Msg.CREATING);
        Reporter.report(this.getSource(), this, Reporter.Msg.ATTACHING);
        this.getSource().addLoad(this);
    }

    /**
     * engage if not engaged already and engage all the loads
     */
    @Override
    public void engage() {
        if (!this.engaged()) {
            super.engage();
            this.engageLoads();
        }
    }

    /**
     * disengage if it's already engaged and disengage all loads
     */
    @Override
    public void disengage() {
        if (this.engaged()) {
            super.disengage();
            this.disengageLoads();
        }
    }

    /**
     * change the draw
     * @param delta rating
     */
    public void changeDraw(int delta) {
        super.changeDraw(delta);
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
        this.getSource().changeDraw(delta);
    }

    /**
     * outlet is not switchable
     * @return false
     */
    public boolean isSwitchOn(){
        return false;
    }

    /**
     * cannot be toggled
     */
    @Override
    public void toggle() {
        return;
    }

}

package components;

/**
 * This is the PowerSource class.
 * It is the highest hierarchy and it is its own source of power.
 *
 * File: PowerSource.java
 * Author: Nhu Quynh Duong, qnd5128
 */
public class PowerSource extends Component {

    /**
     * creates a new PowerSource and instantiate class variables
     * @param name name of the source
     */
    public PowerSource(String name) {
        super(name, null);
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    /**
     * engage if not engaged already and engage all the loads
     */
    public void engage() {
        super.engage();
        Reporter.report(this, Reporter.Msg.ENGAGING);
        super.engageLoads();
    }

    /**
     * change the draw
     * @param delta rating
     */
    public void changeDraw(int delta) {
        super.changeDraw(delta);
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
    }

    /**
     * print out a particular format
     */
    public void display() {
        System.out.println();
        super.display(0);
        System.out.println();
    }

    /**
     * not switchable
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

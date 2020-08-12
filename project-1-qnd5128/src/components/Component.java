package components;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the Component class.
 * The abstraction for all components in the home wiring system
 *
 * File: Component.java
 * Author: Nhu Quynh Duong, qnd5128
 */
public abstract class Component {
    private String name;
    private Component source;
    private boolean isEngaged;
    private int power;
    private Collection<Component> cmps;

    /**
     * Create a new Component. Attach it to its source.
     * @param name the name of this Component
     * @param source the Component providing this one with power
     */
    protected Component(String name, Component source){
        this.name = name;
        this.source = source;
        isEngaged =  false;
        cmps = new ArrayList<>();
        power = 0;
    }

    /**
     * get the component's name
     * @return the component's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Add a new load to this component
     * @param newLoad the new component to be added
     */
    protected void addLoad(Component newLoad) {
        attach(newLoad);
    }

    /**
     * Add a new load to this Component
     * If this component is engaged, the new load becomes engaged.
     * @param load the component to be attached
     */
    protected void attach(Component load) {
        cmps.add(load);
    }

    /**
     * The source for this component is now being powered
     */
    void engage() {
        Reporter.report(this, Reporter.Msg.ENGAGING);
        isEngaged = true;
    }

    /**
     * This Component tells its loads that they can no longer draw current from it
     */
    protected void disengage() {
        Reporter.report(this, Reporter.Msg.DISENGAGING);
        isEngaged = false;
    }

    /**
     * Change the amount of current passing through this Component
     * @param delta The number of amperes
     */
    protected void changeDraw(int delta) {
        power += delta;
    }

    /**
     * disengage all of this component's load
     */
    protected void disengageLoads() {
        for (Component cmp: cmps) {
            cmp.disengage();
        }
    }

    /**
     * engage all of this component's loads
     */
    protected void engageLoads() {
        for (Component cmp: cmps) {
            cmp.engage();
        }
    }

    /**
     * display with indentation
     * @param indent take indentation from PowerSource
     */
    void display(int indent) {
        for (int i = 0; i < indent; ++i) {
            System.out.print(" ");
        }
        System.out.println( " + " + toString());
        if (cmps != null) {
            for (Component cmp : cmps) {
                cmp.display(indent + 4);
            }
        }
    }

    /**
     * return boolean if this Component currently being fed power
     * @return true iff this Component is engaged
     */
    protected boolean engaged() {
        return isEngaged;
    }

    /**
     * Find how much current this component is drawing
     * @return the amount of current this Component is currently drawing from its source
     */
    protected int getDraw() {
        return power;
    }

    /**
     * Get the components load
     * @return an unmodifiable collection of this Component's loads
     */
    protected Collection<Component> getLoads() {
        return cmps;
    }

    /**
     * Get the source power
     * @return the source
     */
    protected Component getSource() {
        return this.source;
    }

    /**
     * Change this Component's draw to the given value
     * @param draw how much current this component is to draw
     */
    protected void setDraw(int draw) {
        power = draw;
    }

    /**
     * Describe a component in the manner of Reporter.identify(Component)
     * @return information about this component
     */
    @Override
    public String toString() {
        return Reporter.identify(this);
    }

    /**
     * Abstract method to determine if it's on
     * @return true if it's on
     */
    protected abstract boolean isSwitchOn();

    /**
     * Abstract method to toggle CircuitBreaker and Appliance
     */
    public abstract void toggle();

}

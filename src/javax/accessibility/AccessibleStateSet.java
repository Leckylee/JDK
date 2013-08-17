/*
 * @(#)AccessibleStateSet.java	1.10 01/11/29
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.accessibility;

import java.util.Vector;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class AccessibleStateSet determines a components state set.  The state set
 * of a component is a set of AccessibleState objects and descriptions the
 * current overall state of the object, such as whether it is enabled, 
 * has focus, etc.
 *
 * @see AccessibleState
 *
 * @version     1.10 11/29/01 23:09:29
 * @author      Willie Walker
 */
public class AccessibleStateSet {

    /**
     * Each entry in the Vector represents an AccessibleState.
     * @see #add
     * @see #addAll
     * @see #remove
     * @see #contains
     * @see #toArray
     * @see #clear
     */
    protected Vector states = null;

    /**
     * Create a new empty state set.
     */
    public AccessibleStateSet() {
        states = null;
    }

    /**
     * Create a new state with the initial set of states contained in 
     * the array of states passed in.  Duplicate entries are ignored.
     * @param state an array of AccessibleState describing the state set.
     */
    public AccessibleStateSet(AccessibleState[] states) {
        if (states.length != 0) {
            this.states = new Vector(states.length);
            for (int i = 0; i < states.length; i++) {
                if (!this.states.contains(states[i])) {
                    this.states.addElement(states[i]);
                }
            }
        }
    }

    /**
     * Add a new state to the current state set if it is not already
     * present.  If the state is already in the state set, the state
     * set is unchanged and the return value is false.  Otherwise, 
     * the state is added to the state set and the return value is
     * true.
     * @param state the state to add to the state set
     * @return true if state is added to the state set; false if the state set 
     * is unchanged
     */
    public boolean add(AccessibleState state) {
        // [[[ PENDING:  WDW - the implementation of this does not need
        // to always use a vector of states.  It could be improved by
        // caching the states as a bit set.]]]
        if (states == null) {
            states = new Vector();
        }

        if (!states.contains(state)) {
            states.addElement(state);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add all of the states to the existing state set.  Duplicate entries 
     * are ignored.
     * @param state  AccessibleState array describing the state set.
     */
    public void addAll(AccessibleState[] states) {
        if (states.length != 0) {
            if (this.states == null) {
		this.states = new Vector(states.length);
            }
            for (int i = 0; i < states.length; i++) {
                if (!this.states.contains(states[i])) {
                    this.states.addElement(states[i]);
                }
            }
        }
    }

    /**
     * Remove a state from the current state set.  If the state is not
     * in the set, the state set will be unchanged and the return value
     * will be false.  If the state is in the state set, it will be removed
     * from the set and the return value will be true.
     *	
     * @param state the state to remove from the state set
     * @return true is the state is in the state set; false if the state set 
     * will be unchanged
     */
    public boolean remove(AccessibleState state) {
        if (states == null) {
            return false;
        } else {
            return states.removeElement(state);
        }
    }

    /**
     * Remove all the states from the current state set.
     */
    public void clear() {
        if (states != null) {
            states.removeAllElements();
        }
    }

    /**
     * Check if the current state is in the state set.
     * @param state the state
     * @return true if the state is in the state set; otherwise false
     */
    public boolean contains(AccessibleState state) {
        if (states == null) {
            return false;
        } else {
            return states.contains(state);
        }
    }

    /**
     * Returns the current state set as an array of AccessibleState
     * @return AccessibleState array conatining the current state.
     */
    public AccessibleState[] toArray() {
        if (states == null) {
            return new AccessibleState[0];
        } else {
            AccessibleState[] stateArray = new AccessibleState[states.size()];
            for (int i = 0; i < stateArray.length; i++) {
                stateArray[i] = (AccessibleState) states.elementAt(i);
            }
            return stateArray;
        }
    }

    /**
     * Create a localized String representing all the states in the set 
     * using the default locale.
     *
     * @return comma separated localized String
     * @see AccessibleBundle#toDisplayString
     */
    public String toString() {
        String ret = null;
        if ((states != null) && (states.size() > 0)) {
            ret = ((AccessibleState) (states.elementAt(0))).toDisplayString();
            for (int i = 1; i < states.size(); i++) {
                ret = ret + "," 
                        + ((AccessibleState) (states.elementAt(i))).
					      toDisplayString();
            }
        }
        return ret;
    }
}

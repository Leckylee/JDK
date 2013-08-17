/*
 * @(#)Border.java	1.13 98/09/21
 *
 * Copyright 1997, 1998 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */
package javax.swing.border;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Component;

/**
 * Interface describing an object capable of rendering a border
 * around the edges of a swing component.
 * <p>
 * In the Swing component set, borders supercede Insets as the
 * mechanism for creating a (decorated or plain) area around the 
 * edge of a component.
 * <p>
 * Usage Notes:
 * <ul>
 * <li>Use EmptyBorder to create a plain border (this mechanism
 *     replaces its predecessor, <code>setInsets</code>).
 * <li>Use CompoundBorder to nest multiple border objects, creating
 *     a single, combined border.
 * <li>Border instances are designed to be shared. Rather than creating
 *     a new border object using one of border classes, use the
 *     BorderFactory methods, which produces a shared instance of the
 *     common border types.
 * <li>Additional border styles include BevelBorder, SoftBevelBorder,
 *     EtchedBorder, LineBorder, TitledBorder, and MatteBorder.
 * <li>To create a new border class, subclass AbstractBorder.   
 * </ul>
 * 
 * @version 1.13 09/21/98
 * @author David Kloba
 * @author Amy Fowler
 * @see EmptyBorder
 * @see CompoundBorder
 */
public interface Border
{
    /**
     * Paints the border for the specified component with the specified 
     * position and size.
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    void paintBorder(Component c, Graphics g, int x, int y, int width, int height);

    /**
     * Returns the insets of the border.  
     * @param c the component for which this border insets value applies
     */
    Insets getBorderInsets(Component c);

    /**
     * Returns whether or not the border is opaque.  If the border
     * is opaque, it is responsible for filling in it's own
     * background when painting.
     */
    boolean isBorderOpaque();
}

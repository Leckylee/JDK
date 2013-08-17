/*
 * @(#)ParagraphView.java	1.18 01/11/29
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package javax.swing.text.html;

import java.awt.*;
import javax.swing.SizeRequirements;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 * Displays the a paragraph, and uses css attributes for its
 * configuration.
 *
 * @author  Timothy Prinzing
 * @version 1.18 11/29/01
 */

public class ParagraphView extends javax.swing.text.ParagraphView {

    /**
     * Constructs a ParagraphView for the given element.
     *
     * @param elem the element that this view is responsible for
     */
    public ParagraphView(Element elem) {
	super(elem);
	StyleSheet sheet = getStyleSheet();
	attr = sheet.getViewAttributes(this);
	painter = sheet.getBoxPainter(attr);
    }

    /**
     * Establishes the parent view for this view.  This is
     * guaranteed to be called before any other methods if the
     * parent view is functioning properly.
     * <p> 
     * This is implemented
     * to forward to the superclass as well as call the
     * <a href="#setPropertiesFromAttributes">setPropertiesFromAttributes</a>
     * method to set the paragraph properties from the css
     * attributes.  The call is made at this time to ensure
     * the ability to resolve upward through the parents 
     * view attributes.
     *
     * @param parent the new parent, or null if the view is
     *  being removed from a parent it was previously added
     *  to
     */
    public void setParent(View parent) {
	super.setParent(parent);
	setPropertiesFromAttributes();
    }

    /**
     * Fetches the attributes to use when rendering.  This is
     * implemented to multiplex the attributes specified in the
     * model with a StyleSheet.
     */
    public AttributeSet getAttributes() {
	return attr;
    }

    /**
     * Gives notification from the document that attributes were changed
     * in a location that this view is responsible for.  This causes the
     * set of view attributes to be recomputed.
     *
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#changedUpdate
     */
    public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
	StyleSheet sheet = getStyleSheet();
	attr = sheet.getViewAttributes(this);
	super.changedUpdate(e, a, f);
    }

    /**
     * Sets up the paragraph from css attributes instead of
     * the values found in StyleConstants (i.e. which are used
     * by the superclass).  Since
     */
    protected void setPropertiesFromAttributes() {
	if (attr != null) {
	    super.setPropertiesFromAttributes();
	    setInsets((short) painter.getInset(TOP, this),
		      (short) painter.getInset(LEFT, this),
		      (short) painter.getInset(BOTTOM, this),
		      (short) painter.getInset(RIGHT, this));
	    Object o = attr.getAttribute(CSS.Attribute.TEXT_ALIGN);
	    if (o != null) {
		// set horizontal alignment
		String ta = o.toString();
		if (ta.equals("left")) {
		    setJustification(StyleConstants.ALIGN_LEFT);
		} else if (ta.equals("center")) {
		    setJustification(StyleConstants.ALIGN_CENTER);
		} else if (ta.equals("right")) {
		    setJustification(StyleConstants.ALIGN_RIGHT);
		} else if (ta.equals("justify")) {
		    setJustification(StyleConstants.ALIGN_JUSTIFIED);
		}
	    }
	}
    }

    protected StyleSheet getStyleSheet() {
	HTMLDocument doc = (HTMLDocument) getDocument();
	return doc.getStyleSheet();
    }


    /**
     * Calculate the needs for the paragraph along the minor axis.
     * This implemented to use the requirements of the superclass,
     * modified slightly to set a minimum span allowed.  Typical
     * html rendering doesn't let the view size shrink smaller than
     * the length of the longest word.  
     */
    protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
	r = super.calculateMinorAxisRequirements(axis, r);

	// PENDING(prinz) Need to make this better so it doesn't require
	// InlineView and works with font changes within the word.

	// find the longest minimum span.
	float min = 0;
	int n = getLayoutViewCount();
	for (int i = 0; i < n; i++) {
	    View v = getLayoutView(i);
	    if (v instanceof InlineView) {
		float wordSpan = ((InlineView) v).getLongestWordSpan();
		min = Math.max(wordSpan, min);
	    } else {
		min = Math.max(v.getMinimumSpan(axis), min);
	    }
	}
	r.minimum = (int) min;
	r.preferred = Math.max(r.minimum,  r.preferred);
	r.maximum = Math.max(r.preferred, r.maximum);
	return r;
    }


    /**
     * Indicates whether or not this view should be 
     * displayed.  If none of the children wish to be
     * displayed and the only visible child is the 
     * break that ends the paragraph, the paragraph
     * will not be considered visible.  Otherwise,
     * it will be considered visible and return true.
     * 
     * @returns true if the paragraph should be displayed.
     */
    public boolean isVisible() {
	
	int n = getLayoutViewCount() - 1;
	for (int i = 0; i < n; i++) {
	    View v = getLayoutView(i);
	    if (v.isVisible()) {
		return true;
	    }
	}
	if (n > 0) {
	    View v = getLayoutView(n);
	    if ((v.getEndOffset() - v.getStartOffset()) == 1) {
		return false;
	    }
	}
	// If it's the last paragraph and not editable, it shouldn't
	// be visible.
	if (getStartOffset() == getDocument().getLength()) {
	    boolean editable = false;
	    Component c = getContainer();
	    if (c instanceof JTextComponent) {
		editable = ((JTextComponent)c).isEditable();
	    }
	    if (!editable) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Renders using the given rendering surface and area on that
     * surface.  This is implemented to delgate to the superclass
     * after stashing the base coordinate for tab calculations.
     *
     * @param g the rendering surface to use
     * @param a the allocated region to render into
     * @see View#paint
     */
    public void paint(Graphics g, Shape a) {
	Rectangle r;
	if (a instanceof Rectangle) {
	    r = (Rectangle) a;
	} else {
	    r = a.getBounds();
	}
	painter.paint(g, r.x, r.y, r.width, r.height, this);
        super.paint(g, a);
    }

    /**
     * Determines the preferred span for this view.  Returns
     * 0 if the view is not visible, otherwise it calls the
     * superclass method to get the preferred span.
     * axis.
     *
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @returns  the span the view would like to be rendered into.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.  
     *           The parent may choose to resize or break the view.
     * @see text.ParagraphView#getPreferredSpan
     */
    public float getPreferredSpan(int axis) {
	if (!isVisible()) {
	    return 0;
	}
	return 	super.getPreferredSpan(axis);
    }

    /**
     * Determines the minimum span for this view along an
     * axis.  Returns 0 if the view is not visible, otherwise 
     * it calls the superclass method to get the minimum span.
     *
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @returns  the minimum span the view can be rendered into.
     * @see text.ParagraphView#getMinimumSpan
     */
    public float getMinimumSpan(int axis) {
	if (!isVisible()) {
	    return 0;
	}
	return super.getMinimumSpan(axis);
    }

    /**
     * Determines the maximum span for this view along an
     * axis.  Returns 0 if the view is not visible, otherwise
     * it calls the superclass method ot get the maximum span.
     *
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @returns  the maximum span the view can be rendered into.
     * @see text.ParagraphView#getMaximumSpan
     */
    public float getMaximumSpan(int axis) {
	if (!isVisible()) {
	    return 0;
	}
	return super.getMaximumSpan(axis);
    }

    private AttributeSet attr;
    private StyleSheet.BoxPainter painter;
}


/*
 * @(#)Label.java	1.41 98/08/31
 *
 * Copyright 1995-1998 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */
package java.awt;

import java.awt.peer.LabelPeer;

/**
 * A <code>Label</code> object is a component for placing text in a
 * container. A label displays a single line of read-only text.
 * The text can be changed by the application, but a user cannot edit it
 * directly.
 * <p>
 * For example, the code&nbsp;.&nbsp;.&nbsp;.
 * <p>
 * <hr><blockquote><pre>
 * setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
 * add(new Label("Hi There!"));
 * add(new Label("Another Label"));
 * </pre></blockquote><hr>
 * <p>
 * produces the following label:
 * <p>
 * <img src="doc-files/Label-1.gif"
 * ALIGN=center HSPACE=10 VSPACE=7>
 *
 * @version	1.41, 08/31/98
 * @author 	Sami Shaio
 * @since       JDK1.0
 */
public class Label extends Component {

    static {
        /* ensure that the necessary native libraries are loaded */
	Toolkit.loadLibraries();
        initIDs();
    }

    /**
     * Indicates that the label should be left justified.
     */
    public static final int LEFT 	= 0;

    /**
     * Indicates that the label should be centered.
     */
    public static final int CENTER 	= 1;

    /**
     * Indicates that the label should be right justified.
     * @since   JDK1.0t.
     */
    public static final int RIGHT 	= 2;

    /**
     * The text of this label.
     * This text can be modified by the program
     * but never by the user.
     *
     * @serial
     * @see getText()
     * @see setText()
     */
    String text;

    /**
     * The label's alignment.  The default alignment is set
     * to be left justified.
     *
     * @serial
     * @see getAlignment()
     * @see setAlignment()
     */
    int	   alignment = LEFT;

    private static final String base = "label";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     */
     private static final long serialVersionUID = 3094126758329070636L;

    /**
     * Constructs an empty label.
     * The text of the label is the empty string <code>""</code>.
     */
    public Label() {
	this("", LEFT);
    }

    /**
     * Constructs a new label with the specified string of text,
     * left justified.
     * @param text the string that the label presents.
     *        A <code>null</code> value
     *        will be accepted without causing a NullPointerException
     *        to be thrown.
     */
    public Label(String text) {
        this(text, LEFT);
    }

    /**
     * Constructs a new label that presents the specified string of
     * text with the specified alignment.
     * Possible values for <code>alignment</code> are <code>Label.LEFT</code>,
     * <code>Label.RIGHT</code>, and <code>Label.CENTER</code>.
     * @param text the string that the label presents.
     *        A <code>null</code> value
     *        will be accepted without causing a NullPointerException
     *        to be thrown.
     * @param     alignment   the alignment value.
     */
    public Label(String text, int alignment) {
	this.text = text;
	setAlignment(alignment);
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is <code>null</code>.
     */
    String constructComponentName() {
        synchronized (getClass()) {
	    return base + nameCounter++;
	}
    }

    /**
     * Creates the peer for this label.  The peer allows us to
     * modify the appearance of the label without changing its
     * functionality.
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
	    if (peer == null)
	        peer = getToolkit().createLabel(this);
	    super.addNotify();
	}
    }

    /**
     * Gets the current alignment of this label. Possible values are
     * <code>Label.LEFT</code>, <code>Label.RIGHT</code>, and
     * <code>Label.CENTER</code>.
     * @see        java.awt.Label#setAlignment
     */
    public int getAlignment() {
	return alignment;
    }

    /**
     * Sets the alignment for this label to the specified alignment.
     * Possible values are <code>Label.LEFT</code>,
     * <code>Label.RIGHT</code>, and <code>Label.CENTER</code>.
     * @param      alignment    the alignment to be set.
     * @exception  IllegalArgumentException if an improper value for
     *                          <code>alignment</code> is given.
     * @see        java.awt.Label#getAlignment
     */
    public synchronized void setAlignment(int alignment) {
	switch (alignment) {
	  case LEFT:
	  case CENTER:
	  case RIGHT:
	    this.alignment = alignment;
    	    LabelPeer peer = (LabelPeer)this.peer;
	    if (peer != null) {
		peer.setAlignment(alignment);
	    }
	    return;
	}
	throw new IllegalArgumentException("improper alignment: " + alignment);
    }

    /** 
     * Gets the text of this label. 
     * @return     the text of this label, or <code>null</code> if 
     *             the text has been set to <code>null</code>.
     * @see        java.awt.Label#setText
     */
    public String getText() {
	return text;
    }

    /**
     * Sets the text for this label to the specified text.
     * @param      text the text that this label displays. If 
     *             <code>text</code> is <code>null</code>, it is 
     *             treated for display purposes like an empty 
     *             string <code>""</code>.
     * @see        java.awt.Label#getText
     */
    public void setText(String text) {
        boolean testvalid = false;

	synchronized (this) {
	    if (text != this.text && (this.text == null ||
				      !this.text.equals(text))) {
	        this.text = text;
		LabelPeer peer = (LabelPeer)this.peer;
		if (peer != null) {
		    peer.setText(text);
		}
		testvalid = true;
	    }
	}

	// This could change the preferred size of the Component.
	if (testvalid && valid) {
	    invalidate();
	}
    }

    /**
     * Returns the parameter string representing the state of this
     * label. This string is useful for debugging.
     * @return     the parameter string of this label.
     */
    protected String paramString() {
	String str = ",align=";
	switch (alignment) {
	  case LEFT:   str += "left"; break;
	  case CENTER: str += "center"; break;
	  case RIGHT:  str += "right"; break;
	}
	return super.paramString() + str + ",text=" + text;
    }

    /**
     * Initialize JNI field and method IDs
     */
    private static native void initIDs();
}

/*
 * @(#)InputMethodEvent.java	1.12 98/06/22
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

package java.awt.event;

import java.awt.AWTEvent;
import java.awt.Component;
import java.lang.Integer;
import java.awt.font.TextHitInfo;
import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;

/**
 * Input method events contain information about text that is being
 * composed using an input method. Whenever the text changes, the
 * input method sends an event. If the text component that's currently
 * using the input method is an active client, the event is dispatched
 * to that component. Otherwise, it is dispatched to a separate
 * composition window.
 *
 * <p>
 * The text included with the input method event consists of two parts:
 * committed text and composed text. Either part may be empty. The two
 * parts together replace any uncommitted composed text sent in previous events,
 * or the currently selected committed text.
 * Committed text should be integrated into the text component's persistent
 * data, it will not be sent again. Composed text may be sent repeatedly,
 * with changes to reflect the user's editing operations. Committed text
 * always precedes composed text.
 *
 * @version 1.12 06/22/98
 * @author JavaSoft Asia/Pacific
 */

public class InputMethodEvent extends AWTEvent {

    /**
     * Marks the first integer id for the range of input method event ids.
     */
    public static final int INPUT_METHOD_FIRST = 1100;

    /**
     * The event type indicating changed input method text. This event is
     * generated by input methods while processing input.
     */
    public static final int INPUT_METHOD_TEXT_CHANGED = INPUT_METHOD_FIRST;

    /**
     * The event type indicating a changed insertion point in input method text.
     * This event is
     * generated by input methods while processing input if only the caret changed.
     */
    public static final int CARET_POSITION_CHANGED = INPUT_METHOD_FIRST + 1;

    /**
     * Marks the last integer id for the range of input method event ids.
     */
    public static final int INPUT_METHOD_LAST = INPUT_METHOD_FIRST + 1;

    // Text object
    private transient AttributedCharacterIterator text;
    private transient int committedCharacterCount;
    private transient TextHitInfo caret;
    private transient TextHitInfo visiblePosition;

    /**
     * Constructs an InputMethodEvent with the specified source component, type,
     * text, caret, and visiblePosition.
     * <p>
     * The offsets of caret and visiblePosition are relative to the current
     * composed text; that is, the composed text within <code>text</code>
     * if this is an <code>INPUT_METHOD_TEXT_CHANGED</code> event,
     * the composed text within the <code>text</code> of the
     * preceding <code>INPUT_METHOD_TEXT_CHANGED</code> event otherwise.
     *
     * @param source The object where the event originated.
     * @param id The event type.
     * @param text The combined committed and composed text, committed text first.
     * Always null for <code>CARET_POSITION_CHANGED</code>;
     * may be null for <code>INPUT_METHOD_TEXT_CHANGED</code> if there's no committed or composed text.
     * @param committedCharacterCount The number of committed characters in the text.
     * @param caret the caret (a.k.a. insertion point).
     * Null if there's no caret within current composed text.
     * @param visiblePosition The position that's most important to be visible.
     * Null if there's no recommendation for a visible position within current composed text.
     * @exception IllegalArgumentException
     * if <code>id</code> is not in the range <code>INPUT_METHOD_FIRST</code>..<code>INPUT_METHOD_LAST</code>,
     * if id is <code>CARET_POSITION_CHANGED</code> and <code>text</code> is not null,
     * or if <code>committedCharacterCount</code> is not in the range <code>0</code>..<code>(text.getEndIndex() - text.getBeginIndex())</code>
     */
    public InputMethodEvent(Component source, int id,
            AttributedCharacterIterator text, int committedCharacterCount,
            TextHitInfo caret, TextHitInfo visiblePosition) {
        super(source, id);
        if (id < INPUT_METHOD_FIRST || id > INPUT_METHOD_LAST) {
            throw new IllegalArgumentException("id outside of valid range");
        }

        if (id == CARET_POSITION_CHANGED && text != null) {
            throw new IllegalArgumentException("text must be null for CARET_POSITION_CHANGED");
        }

        this.text = text;
        int textLength = 0;
        if (text != null) {
            textLength = text.getEndIndex() - text.getBeginIndex();
        }

        if (committedCharacterCount < 0 || committedCharacterCount > textLength) {
            throw new IllegalArgumentException("committedCharacterCount outside of valid range");
        }
        this.committedCharacterCount = committedCharacterCount;

        this.caret = caret;
        this.visiblePosition = visiblePosition;
   }

    /**
     * Constructs an InputMethodEvent with the specified source component, type,
     * caret, and visiblePosition. The text is set to null, committedCharacterCount to 0.
     * <p>
     * The offsets of caret and visiblePosition are relative to the current
     * composed text; that is,
     * the composed text within the <code>text</code> of the
     * preceding <code>INPUT_METHOD_TEXT_CHANGED</code> event
     * if the event being constructed as a <code>CARET_POSITION_CHANGED</code> event.
     * For an <code>INPUT_METHOD_TEXT_CHANGED</code> event without text, caret
     * and visiblePosition must be null.
     *
     * @param source The object where the event originated.
     * @param id The event type.
     * @param caret the caret (a.k.a. insertion point).
     * Null if there's no caret within current composed text.
     * @param visiblePosition The position that's most important to be visible.
     * Null if there's no recommendation for a visible position within current composed text.
     */
    public InputMethodEvent(Component source, int id, TextHitInfo caret,
            TextHitInfo visiblePosition) {
        this(source, id, null, 0, caret, visiblePosition);
    }

    /**
     * Gets the combined committed and composed text.
     * Characters from index 0 to index <code>getCommittedCharacterCount() - 1</code> are committed
     * text, the remaining characters are composed text.
     *
     * @return the text.
     * Always null for CARET_POSITION_CHANGED;
     * may be null for INPUT_METHOD_TEXT_CHANGED if there's no composed or committed text.
     */
    public AttributedCharacterIterator getText() {
        return text;
    }

    /**
     * Gets the number of committed characters in the text.
     */
    public int getCommittedCharacterCount() {
        return committedCharacterCount;
    }

    /**
     * Gets the caret.
     * <p>
     * The offset of the caret is relative to the current
     * composed text; that is, the composed text within getText()
     * if this is an <code>INPUT_METHOD_TEXT_CHANGED</code> event,
     * the composed text within getText() of the
     * preceding <code>INPUT_METHOD_TEXT_CHANGED</code> event otherwise.
     *
     * @return the caret (a.k.a. insertion point).
     * Null if there's no caret within current composed text.
     */
    public TextHitInfo getCaret() {
        return caret;
    }

    /**
     * Gets the position that's most important to be visible.
     * <p>
     * The offset of the visible position is relative to the current
     * composed text; that is, the composed text within getText()
     * if this is an <code>INPUT_METHOD_TEXT_CHANGED</code> event,
     * the composed text within getText() of the
     * preceding <code>INPUT_METHOD_TEXT_CHANGED</code> event otherwise.
     *
     * @return the position that's most important to be visible.
     * Null if there's no recommendation for a visible position within current composed text.
     */
    public TextHitInfo getVisiblePosition() {
        return visiblePosition;
    }

    /**
     * Consumes this event so that it will not be processed
     * in the default manner by the source which originated it.
     */
    public void consume() {
        consumed = true;
    }

    /**
     * Returns whether or not this event has been consumed.
     * @see #consume
     */
    public boolean isConsumed() {
        return consumed;
    }
    
    /**
     * Returns a parameter string identifying this event.
     * This method is useful for event-logging and for debugging.
     * It contains the event ID in text form, the characters of the
     * committed and composed text
     * separated by "+", the number of committed characters,
     * the caret, and the visible position.
     *
     * @return a string identifying the event and its attributes
     */
    public String paramString() {
        String typeStr;
        switch(id) {
          case INPUT_METHOD_TEXT_CHANGED:
              typeStr = "INPUT_METHOD_TEXT_CHANGED";
              break;
          case CARET_POSITION_CHANGED:
              typeStr = "CARET_POSITION_CHANGED";
              break;
          default:
              typeStr = "unknown type";
        }

        String textString;
        if (text == null) {
            textString = "no text";
        } else {
            StringBuffer textBuffer = new StringBuffer("\"");
            int committedCharacterCount = this.committedCharacterCount;
            char c = text.first();
            while (committedCharacterCount-- > 0) {
                textBuffer.append(c);
                c = text.next();
            }
            textBuffer.append("\" + \"");
            while (c != CharacterIterator.DONE) {
                textBuffer.append(c);
                c = text.next();
            }
            textBuffer.append("\"");
            textString = textBuffer.toString();
        }
        
        String countString = committedCharacterCount + " characters committed";
        
        String caretString;
        if (caret == null) {
            caretString = "no caret";
        } else {
            caretString = "caret: " + caret.toString();
        }
        
        String visiblePositionString;
        if (visiblePosition == null) {
            visiblePositionString = "no visible position";
        } else {
            visiblePositionString = "visible position: " + visiblePosition.toString();
        }
        
        return typeStr + ", " + textString + ", " + countString + ", " + caretString + ", " + visiblePositionString;
    }
}

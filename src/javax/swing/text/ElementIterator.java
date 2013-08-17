/*
 * @(#)ElementIterator.java	1.7 01/11/29
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.swing.text;

import java.util.Stack;
import java.util.Enumeration;

/**
 * <p>
 * ElementIterator, as the name suggests, iteratates over the Element
 * tree.  The constructor can be invoked with either Document or an Element
 * as an argument.  If the constructor is invoked with a Document as an
 * argument then the root of the iteration is the return value of
 * document.getDefaultRootElement().
 *
 * The iteration happens in a depth-first manner.  In terms of how
 * boundary conditions are handled:
 * a) if next() is called before first() or current(), the
 *    root will be returned.
 * b) next() returns null to indicate the end of the list.
 * c) previous() returns null when the current element is the root
 *    or next() has returned null.
 *
 * The ElementIterator does no locking of the Element tree. This means
 * that it does not track any changes.  It is the responsibility of the
 * user of this class, to ensure that no changes happen during element
 * iteration.
 *
 * Simple usage example:
 *
 *    public void iterate() {
 *        ElementIterator it = new ElementIterator(root);
 *	  Element elem;
 *	  while (true) {
 *           if ((elem = next()) != null) {
 *		 // process element
 *		 System.out.println("elem: " + elem.getName());
 *	     } else {
 *	         break;
 *	     }
 *	  }
 *    }
 *
 * @author Sunita Mani
 * @version 1.7 11/29/01
 *
 */

public class ElementIterator implements Cloneable {


    private Element root;
    private Stack elementStack = null;

    /**
     * The StackItem class stores the element
     * as well as a child index.  If the
     * index is -1, then the element represented
     * on the stack is the element itself.
     * Otherwise, the index functions as as index
     * into the vector of children of the element.
     * In this case, the item on the stack
     * represents the "index"th child of the element
     *
     */
    private class StackItem implements Cloneable {
	Element item;
	int childIndex;

	private StackItem(Element elem) {
	    /**
	     * -1 index implies a self reference,
	     * as opposed to an index into its
	     * list of children.
	     */
	    this.item = elem;
	    this.childIndex = -1;
	}

	private void incrementIndex() {
	    childIndex++;
	}

	private Element getElement() {
	    return item;
	}

	private int getIndex() {
	    return childIndex;
	}

	protected Object clone() throws java.lang.CloneNotSupportedException {
	    return super.clone();
	}
    }

    /**
     * Creates a new ElementIterator. The
     * root element is taken to get the
     * default root element of the document.
     *
     * @param a Document.
     */
    public ElementIterator(Document document) {
	root = document.getDefaultRootElement();
    }


    /**
     * Creates a new ElementIterator.
     *
     * @param the root Element.
     */
    public ElementIterator(Element root) {
	this.root = root;
    }


    /**
     * Clones the ElementIterator.
     *
     * @return a cloned ElementIterator Object.
     */
    public synchronized Object clone() {

	try {
	    ElementIterator it = new ElementIterator(root);
	    it.elementStack = new Stack();
	    for (int i = 0; i < elementStack.size(); i++) {
		StackItem item = (StackItem)elementStack.elementAt(i);
		StackItem clonee = (StackItem)item.clone();
		it.elementStack.push(clonee);
	    }
	    return it;
	} catch (CloneNotSupportedException e) {
	    throw new InternalError();
	}
    }


    /**
     * Fetches the first element.
     *
     * @return an Element.
     */
    public Element first() {
	// just in case...
	if (root == null) {
	    return null;
	}

	elementStack = new Stack();
	if (root.getElementCount() != 0) {
	    elementStack.push(new StackItem(root));
	}
	return root;
    }

    /**
     * Fetches the current depth of element tree.
     *
     * @return the depth.
     */
    public int depth() {
	return elementStack.size();
    }


    /**
     * Fetches the current Element.
     *
     * @returns element on top of the stack or
     *          null if the root element is null.
     */
    public Element current() {

	if (elementStack == null) {
	    return first();
	}

	/*
	  get a handle to the element on top of the stack.
	*/
	if (! elementStack.empty()) {
	    StackItem item = (StackItem)elementStack.peek();
	    Element elem = item.getElement();
	    int index = item.getIndex();
	    // self reference
	    if (index == -1) {
		return elem;
	    }
	    // return the child at location "index".
	    return elem.getElement(index);
	}
	return null;
    }


    /**
     * Fetches the next Element. The strategy
     * used to locate the next element is
     * a depth-first search.
     *
     * @returns the next element or null
     *          at the end of the list.
     */
    public Element next() {

	/* if current() has not been invoked
	   and next is invoked, the very first
	   element will be returned. */
	if (elementStack == null) {
	    return first();
	}

	// no more elements
	if (elementStack.isEmpty()) {
	    return null;
	}

	// get a handle to the element on top of the stack

	StackItem item = (StackItem)elementStack.peek();
	Element elem = item.getElement();
	int index = item.getIndex();

	if (index+1 < elem.getElementCount()) {
	    Element child = elem.getElement(index+1);
	    if (child.isLeaf()) {
		/* In this case we merely want to increment
		   the child index of the item on top of the
		   stack.*/
		item.incrementIndex();
	    } else {
		/* In this case we need to push the child(branch)
		   on the stack so that we can iterate over its
		   children. */
		elementStack.push(new StackItem(child));
	    }
	    return child;
	} else {
	    /* No more children for the item on top of the
	       stack therefore pop the stack. */
	    elementStack.pop();
	    if (!elementStack.isEmpty()) {
		/* Increment the child index for the item that
		   is now on top of the stack. */
		StackItem top = (StackItem)elementStack.peek();
		top.incrementIndex();
		/* We now want to return its next child, therefore
		   call next() recursively. */
		return next();
	    }
	}
	return null;
    }


    /**
     * Fetches the previous Element. If howver the current
     * element is the last element, or the current element
     * is null, then null is returned.
     *
     * @returns previous Element if available.
     *
     */
    public Element previous() {

	if (elementStack == null | elementStack.size() == 1) {
	    return null;
	}

	// get a handle to the element on top of the stack
	//
	StackItem item = (StackItem)elementStack.peek();
	Element elem = item.getElement();
	int index = item.getIndex();

	if (index > 0) {
	    /* return child at previous index. */
	    return elem.getElement(--index);
	} else if (index == 0) {
	    /* this implies that current is the element's
	       first child, therefore previous is the
	       element itself. */
	    return elem;
	} else if (index == -1) {
	    /* We need to return either the item
	       below the top item or one of the
	       former's children. */
	    Object top = elementStack.pop();
	    item = (StackItem)elementStack.peek();

	    // restore the top item.
	    elementStack.push(top);
	    elem = item.getElement();
	    index = item.getIndex();
	    return ((index == -1) ? elem : elem.getElement(index));
	}
	// should never get here.
	return null;
    }


    /*
      Iterates through the element tree and prints
      out each element and its attributes.
    */
    private void dumpTree() {

	Element elem;
	while (true) {
	    if ((elem = next()) != null) {
		System.out.println("elem: " + elem.getName());
		AttributeSet attr = elem.getAttributes();
		String s = "";
		Enumeration names = attr.getAttributeNames();
		while (names.hasMoreElements()) {
		    Object key = names.nextElement();
		    Object value = attr.getAttribute(key);
		    if (value instanceof AttributeSet) {
			// don't go recursive
			s = s + key + "=**AttributeSet** ";
		    } else {
			s = s + key + "=" + value + " ";
		    }
		}
		System.out.println("attributes: " + s);
	    } else {
		break;
	    }
	}
    }
}

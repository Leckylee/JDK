/*
 * %W% %E%
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

package javax.accessibility;

import java.util.ListResourceBundle;

/**
 * A resource bundle containing the localized strings in the accessibility 
 * package.  This is meant only for internal use by Java Accessibility and
 * is not meant to be used by assistive technologies or applications.
 *
 * @version     %I% %G% %U%
 * @author      Willie Walker
 */
public class AccessibleResourceBundle extends ListResourceBundle {

    /**
     * Returns the mapping between the programmatic keys and the
     * localized display strings.
     */
    public Object[][] getContents() {
	return contents;
    }

    /** 
     * The table holding the mapping between the programmatic keys
     * and the display strings for the en_US locale.
     */
    static final Object[][] contents = {
    // LOCALIZE THIS
        // Role names
//        { "application","application" },
//        { "border","border" },
//        { "checkboxmenuitem","check box menu item" },
//        { "choice","choice" },
//        { "column","column" },
//        { "cursor","cursor" },
//        { "document","document" },
//        { "grouping","grouping" },
//        { "image","image" },
//        { "indicator","indicator" },
//        { "listitem","list item" },
//        { "radiobuttonmenuitem","radio button menu item" },
//        { "row","row" },
//        { "tablecell","table cell" },
//        { "treenode","tree node" },
        { "alert","alert" },
        { "awtcomponent","AWT component" },
        { "checkbox","check box" },
        { "colorchooser","color chooser" },
        { "columnheader","column header" },
        { "combobox","combo box" },
        { "desktopicon","desktop icon" },
        { "desktoppane","desktop pane" },
        { "dialog","dialog" },
        { "directorypane","directory pane" },
        { "glasspane","glass pane" },
        { "filechooser","file chooser" },
        { "filler","filler" },
        { "frame","frame" },
        { "internalframe","internal frame" },
        { "label","label" },
        { "layeredpane","layered pane" },
        { "list","list" },
        { "menubar","menu bar" },
        { "menu","menu" },
        { "menuitem","menu item" },
        { "optionpane","option pane" },
        { "pagetab","page tab" },
        { "pagetablist","page tab list" },
        { "panel","panel" },
	{ "passwordtext","password text" },
        { "popupmenu","popup menu" },
        { "progressbar","progress bar" },
        { "pushbutton","push button" },
        { "radiobutton","radio button" },
        { "rootpane","root pane" },
        { "rowheader","row header" },
        { "scrollbar","scroll bar" },
        { "scrollpane","scroll pane" },
        { "separator","separator" },
        { "slider","slider" },
        { "splitpane","split pane" },
        { "swingcomponent","swing component" },
        { "table","table" },
        { "text","text" },
        { "tree","tree" },
        { "togglebutton","toggle button" },
        { "toolbar","tool bar" },
        { "tooltip","tool tip" },
        { "unknown","unknown" },
        { "viewport","viewport" },
        { "window","window" },
        // State modes
        { "active","active" },
        { "armed","armed" },
        { "busy","busy" },
        { "checked","checked" },
	{ "collapsed", "collapsed" },
        { "editable","editable" },
	{ "expandable", "expandable" },
	{ "expanded", "expanded" },
        { "enabled","enabled" },
        { "focusable","focusable" },
        { "focused","focused" },
	{ "iconified", "iconified" },
	{ "modal", "modal" },
	{ "multiline", "multiple line" },
        { "multiselectable","multiselectable" },
	{ "opaque", "opaque" },
        { "pressed","pressed" },
	{ "resizable", "resizable" },
        { "selectable","selectable" },
        { "selected","selected" },
        { "showing","showing" },
	{ "singleline", "single line" },
	{ "transient", "transient" },
        { "visible","visible" },
        { "vertical","vertical" },
        { "horizontal","horizontal" }
    // END OF MATERIAL TO LOCALIZE
    };
}

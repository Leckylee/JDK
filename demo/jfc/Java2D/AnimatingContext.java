/*
 * @(#)AnimatingContext.java	1.2 98/09/13
 *
 * Copyright 1998 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */


/**
 * The interface for DemoSurface's that wish to animate.
 */
public interface AnimatingContext {
        public void step(int w, int h);
        public void reset(int newwidth, int newheight);
}

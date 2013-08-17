/*
 * @(#)DynamicImplementation.java	1.10 98/10/11
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

package org.omg.CORBA;

import org.omg.CORBA.portable.ObjectImpl;

/**
 * The base class for all object implementations using the DSI.
 * It defines a single abstract method,
 * <code>invoke</code>, that a dynamic servant needs to implement.
 * DynamicImplementation has been deprecated by the OMG in favor of
 * the Portable Object Adapter.
 *
 * @version 1.6, 09/09/97
 * @see org.omg.CORBA.ServerRequest
 * @since JDK1.2
 */

public abstract
class DynamicImplementation extends org.omg.CORBA.portable.ObjectImpl {

  /**
   * Accepts a <code>ServerRequest</code> object and uses its methods to
   * determine the request target, operation, and parameters, and to
   * set the result or exception.
   * Deprecated by the Portable Object Adapter.
   *
   * @param request             a <code>ServerRequest</code> object representing
   *                            the request to be invoked
   *
   */

  public abstract void invoke(ServerRequest request);
}

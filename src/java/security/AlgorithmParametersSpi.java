/*
 * @(#)AlgorithmParametersSpi.java	1.3 98/03/18
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
 
package java.security;

import java.io.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/**
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the <code>AlgorithmParameters</code> class, which is used to manage
 * algorithm parameters.
 *
 * <p> All the abstract methods in this class must be implemented by each 
 * cryptographic service provider who wishes to supply parameter management
 * for a particular algorithm.
 *
 * @author Jan Luehe
 *
 * @version 1.3, 00/05/10
 *
 * @see AlgorithmParameters
 * @see java.security.spec.AlgorithmParameterSpec
 * @see java.security.spec.DSAParameterSpec
 *
 * @since JDK1.2
 */

public abstract class AlgorithmParametersSpi {

    /**
     * Initializes this parameters object using the parameters 
     * specified in <code>paramSpec</code>.
     *
     * @param paramSpec the parameter specification.
     *
     * @exception InvalidParameterSpecException if the given parameter
     * specification is inappropriate for the initialization of this parameter
     * object.
     */
    protected abstract void engineInit(AlgorithmParameterSpec paramSpec) 
	throws InvalidParameterSpecException;

    /**
     * Imports the specified parameters and decodes them
     * according to the primary decoding format for parameters.
     * The primary decoding format for parameters is ASN.1, if an ASN.1
     * specification for this type of parameters exists.
     *
     * @param params the encoded parameters.
     *
     * @exception IOException on decoding errors
     */
    protected abstract void engineInit(byte[] params)
	throws IOException;

    /**
     * Imports the parameters from <code>params</code> and
     * decodes them according to the specified decoding format.
     * If <code>format</code> is null, the
     * primary decoding format for parameters is used. The primary decoding
     * format is ASN.1, if an ASN.1 specification for these parameters
     * exists.
     *
     * @param params the encoded parameters.
     *
     * @param format the name of the decoding format.
     *
     * @exception IOException on decoding errors
     */
    protected abstract void engineInit(byte[] params, String format)
	throws IOException;

    /**
     * Returns a (transparent) specification of this parameters
     * object.
     * <code>paramSpec</code> identifies the specification class in which 
     * the parameters should be returned. It could, for example, be
     * <code>DSAParameterSpec.class</code>, to indicate that the
     * parameters should be returned in an instance of the 
     * <code>DSAParameterSpec</code> class.
     *
     * @param paramSpec the the specification class in which 
     * the parameters should be returned.
     *
     * @return the parameter specification.
     *
     * @exception InvalidParameterSpecException if the requested parameter
     * specification is inappropriate for this parameter object.
     */
    protected abstract
	AlgorithmParameterSpec engineGetParameterSpec(Class paramSpec)
	throws InvalidParameterSpecException;

    /**
     * Returns the parameters in their primary encoding format.
     * The primary encoding format for parameters is ASN.1, if an ASN.1
     * specification for this type of parameters exists.
     *
     * @return the parameters encoded using the specified encoding scheme.
     *
     * @exception IOException on encoding errors.
     */
    protected abstract byte[] engineGetEncoded() throws IOException;

    /**
     * Returns the parameters encoded in the specified format.
     * If <code>format</code> is null, the
     * primary encoding format for parameters is used. The primary encoding
     * format is ASN.1, if an ASN.1 specification for these parameters
     * exists.
     *
     * @param format the name of the encoding format.
     *
     * @return the parameters encoded using the specified encoding scheme.
     *
     * @exception IOException on encoding errors.
     */
    protected abstract byte[] engineGetEncoded(String format)
	throws IOException;

    /**
     * Returns a formatted string describing the parameters.
     *
     * @return a formatted string describing the parameters.
     */
    protected abstract String engineToString();
}

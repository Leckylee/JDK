/*
 * @(#)Byte.java	1.13 98/09/21
 *
 * Copyright 1996-1998 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package java.lang;

/**
 *
 * The Byte class is the standard wrapper for byte values.
 *
 * @author  Nakul Saraiya
 * @version 1.13, 09/21/98
 * @see     java.lang.Number
 * @since   JDK1.1
 */
public final class Byte extends Number implements Comparable {

    /**
     * The minimum value a Byte can have.
     */
    public static final byte   MIN_VALUE = -128;

    /**
     * The maximum value a Byte can have.
     */
    public static final byte   MAX_VALUE = 127;

    /**
     * The Class object representing the primitive type byte.
     */
    public static final Class	TYPE = Class.getPrimitiveClass("byte");

    /**
     * Returns a new String object representing the specified Byte. The radix
     * is assumed to be 10.
     *
     * @param b	the byte to be converted
     */
    public static String toString(byte b) {
	return Integer.toString((int)b, 10);
    }

    /**
     * Assuming the specified String represents a byte, returns
     * that byte's value. Throws an exception if the String cannot
     * be parsed as a byte.  The radix is assumed to be 10.
     *
     * @param s		the String containing the byte
     * @exception	NumberFormatException If the string does not
     *			contain a parsable byte.
     */
    public static byte parseByte(String s) throws NumberFormatException {
	return parseByte(s, 10);
    }

    /**
     * Assuming the specified String represents a byte, returns
     * that byte's value. Throws an exception if the String cannot
     * be parsed as a byte.
     *
     * @param s		the String containing the byte
     * @param radix	the radix to be used
     * @exception	NumberFormatException If the String does not
     *			contain a parsable byte.
     */
    public static byte parseByte(String s, int radix)
	throws NumberFormatException {
	int i = Integer.parseInt(s, radix);
	if (i < MIN_VALUE || i > MAX_VALUE)
	    throw new NumberFormatException();
	return (byte)i;
    }

    /**
     * Assuming the specified String represents a byte, returns a
     * new Byte object initialized to that value.  Throws an
     * exception if the String cannot be parsed as a byte.
     *
     * @param s		the String containing the integer
     * @param radix 	the radix to be used
     * @exception	NumberFormatException If the String does not
     *			contain a parsable byte.
     */
    public static Byte valueOf(String s, int radix)
	throws NumberFormatException {
	return new Byte(parseByte(s, radix));
    }

    /**
     * Assuming the specified String represents a byte, returns a
     * new Byte object initialized to that value.  Throws an
     * exception if the String cannot be parsed as a byte.
     * The radix is assumed to be 10.
     *
     * @param s		the String containing the integer
     * @exception	NumberFormatException If the String does not
     *			contain a parsable byte.
     */
    public static Byte valueOf(String s) throws NumberFormatException {
	return valueOf(s, 10);
    }

    /**
     * Decodes a String into a Byte.  The String may represent
     * decimal, hexadecimal, and octal numbers.
     *
     * @param nm the string to decode
     */
    public static Byte decode(String nm) throws NumberFormatException {
	if (nm.startsWith("0x")) {
	    return Byte.valueOf(nm.substring(2), 16);
	}
	if (nm.startsWith("#")) {
	    return Byte.valueOf(nm.substring(1), 16);
	}
	if (nm.startsWith("0") && nm.length() > 1) {
	    return Byte.valueOf(nm.substring(1), 8);
	}

	return Byte.valueOf(nm);
    }

    /**
     * The value of the Byte.
     *
     * @serial
     */
    private byte value;

    /**
     * Constructs a Byte object initialized to the specified byte value.
     *
     * @param value	the initial value of the Byte
     */
    public Byte(byte value) {
	this.value = value;
    }

    /**
     * Constructs a Byte object initialized to the value specified by the
     * String parameter.  The radix is assumed to be 10.
     *
     * @param s		the String to be converted to a Byte
     * @exception	NumberFormatException If the String does not
     *			contain a parsable byte.
     */
    public Byte(String s) throws NumberFormatException {
	this.value = parseByte(s);
    }

    /**
     * Returns the value of this Byte as a byte.
     */
    public byte byteValue() {
	return value;
    }

    /**
     * Returns the value of this Byte as a short.
     */
    public short shortValue() {
	return (short)value;
    }

    /**
     * Returns the value of this Byte as an int.
     */
    public int intValue() {
	return (int)value;
    }

    /**
     * Returns the value of this Byte as a long.
     */
    public long longValue() {
	return (long)value;
    }

    /**
     * Returns the value of this Byte as a float.
     */
    public float floatValue() {
	return (float)value;
    }

    /**
     * Returns the value of this Byte as a double.
     */
    public double doubleValue() {
	return (double)value;
    }

    /**
     * Returns a String object representing this Byte's value.
     */
    public String toString() {
	return String.valueOf((int)value);
    }

    /**
     * Returns a hashcode for this Byte.
     */
    public int hashCode() {
	return (int)value;
    }

    /**
     * Compares this object to the specified object.
     *
     * @param obj	the object to compare with
     * @return 		true if the objects are the same; false otherwise.
     */
    public boolean equals(Object obj) {
	if ((obj != null) && (obj instanceof Byte)) {
	    return value == ((Byte)obj).byteValue();
	}
	return false;
    }

    /**
     * Compares two Bytes numerically.
     *
     * @param   anotherByte   the <code>Byte</code> to be compared.
     * @return  the value <code>0</code> if the argument Byte is equal to
     *          this Byte; a value less than <code>0</code> if this Byte
     *          is numerically less than the Byte argument; and a
     *          value greater than <code>0</code> if this Byte is
     *          numerically greater than the Byte argument (signed comparison).
     * @since   JDK1.2
     */
    public int compareTo(Byte anotherByte) {
	return this.value - anotherByte.value;
    }

    /**
     * Compares this Byte to another Object.  If the Object is a Byte,
     * this function behaves like <code>compareTo(Byte)</code>.  Otherwise,
     * it throws a <code>ClassCastException</code> (as Bytes are comparable
     * only to other Bytes).
     *
     * @param   o the <code>Object</code> to be compared.
     * @return  the value <code>0</code> if the argument is a Byte
     *		numerically equal to this Byte; a value less than
     *		<code>0</code> if the argument is a Byte numerically
     *		greater than this Byte; and a value greater than
     *		<code>0</code> if the argument is a Byte numerically
     *		less than this Byte.
     * @exception <code>ClassCastException</code> if the argument is not a
     *		  <code>Byte</code>. 
     * @see     java.lang.Comparable
     * @since   JDK1.2
     */
    public int compareTo(Object o) {
	return compareTo((Byte)o);
    }

    /** use serialVersionUID from JDK 1.1. for interoperability */
    private static final long serialVersionUID = -7183698231559129828L;

}

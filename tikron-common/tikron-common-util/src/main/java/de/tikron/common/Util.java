/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikron.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

/**
 * Gemeinsame Hilfsfunktionen.
 * 
 * @date 21.12.2008
 * @author Titus Kruse
 */
public class Util {

	public static final String EMPTY_STRING = "";

	/**
	 * Liefert true, falls die übergebene Zeichenfolge null oder leer ist.
	 * 
	 * @param s Die Zeichenfolge.
	 * @return true, falls null oder leer.
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.equals(EMPTY_STRING);
	}

	/**
	 * Liefert null, falls die übergebene Zeichenfolge null oder leer ist.
	 * 
	 * @param s Die Zeichenfolge.
	 * @return null oder die Zeichenfolge.
	 */
	public static String nullIfEmpty(String s) {
		return isEmpty(s) ? null : s;
	}

	/**
	 * Vergleicht zwei Objekte unter Berücksichtigung von null-Werten.
	 * 
	 * @param o1 Objekt 1.
	 * @param o2 Objekt 2.
	 * @return true, falls beide Objekte null sind oder falls beide Objekte nicht null und gleich im Sinne von equals()
	 *         sind.
	 */
	public static boolean safeEquals(Object o1, Object o2) {
		if (o1 == null && o2 == null)
			return true;
		else if (o1 != null && o2 != null)
			return o1.equals(o2);
		else
			return false;
	}

	/**
	 * Returns a random generated number within a specified range. The seed will be taken from the current time.
	 * 
	 * @param rangeFrom The generated number will be greater or equal than this number.
	 * @param rangeTo The generated number will be lower than this number.
	 * @return The generated number.
	 */
	public static int randomNumber(int rangeFrom, int rangeTo) {
		Random random = new Random(new Date().getTime());
		return rangeFrom + random.nextInt(rangeTo - rangeFrom);
	}

	/**
	 * Closes an InputStream safely.
	 * 
	 * @param is The InputStream.
	 */
	public static void close(InputStream is) {
		if (is != null)
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Closes an OutputStream safely.
	 * 
	 * @param is The OutputStream.
	 */
	public static void close(OutputStream is) {
		if (is != null)
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Closes a FileInputStream safely.
	 * 
	 * @param fis The FileInputStream.
	 */
	public static void close(FileInputStream fis) {
		if (fis != null)
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Closes a FileOutputStream safely.
	 * 
	 * @param fos The FileOutputStream.
	 */
	public static void close(FileOutputStream fos) {
		if (fos != null)
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}

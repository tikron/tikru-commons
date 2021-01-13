/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Utility class simplified de/encoding of URLs. UTF-8 will be used as stated by W3C.
 *
 * @author Titus Kruse
 * @since 27.07.2015
 */
public class URLCoder {
	
	private static final String ENCODING = "UTF-8";
	
	/**
	 * Decodes an URL. See {@link java.net.URLDecoder} for more information.
	 * 
	 * @param s The URL to decode.
	 * @return The decoded URL.
	 */
	public static String decode(String s) {
		try {
			return URLDecoder.decode(s, ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(String.format("%s not supported", ENCODING));
		}
	}
	
	/**
	 * Encodes an URL. See {@link java.net.URLEncoder} for more information.
	 * 
	 * @param s The URL to encode.
	 * @return The encoded URL.
	 */
	public static String encode(String s) {
		try {
			return URLEncoder.encode(s, ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(String.format("%s not supported", ENCODING));
		}
	}

}

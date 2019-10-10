/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.validation;

/**
 * Defines some constants used for validation.
 *
 * @date 13.05.2015
 * @author Titus Kruse
 */
public final class ValidationConstants {

	private ValidationConstants() {};

// Replaced by simple pattern because of endless loop in JSR303 bean validation in hibernate with this complex pattern.
//	public static final String EMAIL_MASK = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?";
	// @see http://www.regular-expressions.info/email.html
	public static final String EMAIL_MASK = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
	
	// @see http://stackoverflow.com/questions/163360/regular-expression-to-match-urls-in-java
	public static final String URL_MASK = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	public static final String URL_MASK_DE = "(https?:\\/\\/)?([\\dA-Za-z-]+\\.)+(at|ch|de)([\\/\\w \\.-]*)*\\/?";

}

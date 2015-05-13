/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.jpa.validation;

/**
 * Defines some constants used for validation.
 *
 * @date 13.05.2015
 * @author Titus Kruse
 */
public interface ValidationConstants {

	public static final String EMAIL_MASK = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?";
	
	public static final String URL_MASK = "(https?:\\/\\/)?([\\dA-Za-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?";

}

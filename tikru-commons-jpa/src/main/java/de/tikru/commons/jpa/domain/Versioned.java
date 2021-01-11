/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.domain;

/**
 * Declares a persistent object as versioned. A versioned object has a version number used for optimistic locking.
 *
 * @since 19.03.2015
 * @author Titus Kruse
 */
public interface Versioned {

	/**
	 * Returns the version nummber of the entity object.
	 * 
	 * @return The version number.
	 */
	public Integer getVersion();

}

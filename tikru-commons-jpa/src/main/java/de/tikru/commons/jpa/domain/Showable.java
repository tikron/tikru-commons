/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.domain;

/**
 * Declares domain objects able to be shown to human persons like a user. 
 *
 * @author Titus Kruse
 * @since 28.04.2015
 */
public interface Showable {
	
	/**
	 * Returns a normally unique name or title.
	 * 
	 * @return The display name.
	 */
	public String getDisplayName();

}

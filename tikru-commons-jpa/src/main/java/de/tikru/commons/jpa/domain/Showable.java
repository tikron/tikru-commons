/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.domain;

/**
 * Declares domain objects able to be shown to human persons like a user. 
 *
 * @date 28.04.2015
 * @author Titus Kruse
 */
public interface Showable {
	
	/**
	 * Returns a normally unique name or title.
	 * 
	 * @return The display name.
	 */
	public String getDisplayName();

}

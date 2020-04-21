/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikru.commons.jpa.domain;

import java.io.Serializable;

/**
 * A persistent object identified by a unique key. (The opposite if a value object.) This is typically the primary key
 * of the underlying database table.
 * 
 * @param ID The type of the unique identifier used to access the entity.
 *
 * @date 25.03.2009
 * @author Titus Kruse
 */
public interface Entity<ID extends Serializable> extends Serializable {

	/**
	 * Returns the unique identifier of the entity.
	 * 
	 * @return The identifier.
	 */
	public ID getId();

}

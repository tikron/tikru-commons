/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.jpa.domain;

import java.io.Serializable;

/**
 * Deklariert Methoden, die von allen Entitäten angeboten werden müssen.
 *
 * @date 25.03.2009
 * @author Titus Kruse
 */
public interface Entity<ID extends Serializable> extends Serializable {

	/**
	 * Liefert die ID der Entität.
	 * 
	 * @return Die ID.
	 */
	public ID getId();

	/**
	 * Liefert die Version der Entität.
	 * 
	 * @return Die Version.
	 */
	public Integer getVersion();

}

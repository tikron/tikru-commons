/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.domain;

import java.time.LocalDateTime;

/**
 * Declares a persistent object as historical. The object has a timestamp created.
 *
 * @author Titus Kruse
 * @since 19.03.2015
 */
public interface Historical {
	
	public LocalDateTime getCreatedOn();

}

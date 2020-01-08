/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.domain;

import java.time.LocalDateTime;

/**
 * Declares a persistent object as historical. The object has a timestamp created.
 *
 * @date 19.03.2015
 * @author Titus Kruse
 */
public interface Historical {
	
	public LocalDateTime getCreatedOn();

}
/**
	* Copyright (c) 2020 by Titus Kruse.
	*/

package de.tikru.commons.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
	* Default messenger currently ignoring messages
	*
	* @author Titus Kruse
	* @since Dec 16, 2020
	*/

public class DefaultMessenger extends BaseMessenger {

	@Override
	public void notify(String message, String subject) throws MessagingException {
		// Do nothing
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).build();
	}
}

/**
	* Copyright (c) 2020 by Titus Kruse.
	*/

package de.tikru.commons.util.message;

/**
	* Default messenger currently ignoring messages
	*
	* @date Dec 16, 2020
	* @author Titus Kruse
	*/

public class DefaultMessenger extends BaseMessenger {

	@Override
	public void notify(String message, String subject) throws MessagingException {
		// Do nothing
	}
}

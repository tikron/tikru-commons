/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.common.message;

/**
 * Indicates an exception thrown after a runtime error while sending a message.
 *
 * @date 24.06.2015
 * @author Titus Kruse
 */
public class MessengerException extends RuntimeException {

	public MessengerException() {
		super();
	}

	public MessengerException(String message) {
		super(message);
	}

	public MessengerException(Throwable cause) {
		super(cause);
	}

}

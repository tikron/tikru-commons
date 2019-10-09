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
public class MessagerException extends RuntimeException {

	public MessagerException() {
		super();
	}

	public MessagerException(String message) {
		super(message);
	}

	public MessagerException(Throwable cause) {
		super(cause);
	}

}

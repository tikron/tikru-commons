/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.message;

/**
 * Indicates an exception thrown after a runtime error while sending a message.
 *
 * @author Titus Kruse
 * @since 24.06.2015
 */
public class MessagingException extends RuntimeException {

	private static final long serialVersionUID = -2110742001498758149L;

	public MessagingException() {
		super();
	}

	public MessagingException(String message) {
		super(message);
	}

	public MessagingException(Throwable cause) {
		super(cause);
	}

	public MessagingException(String message, Throwable cause) {
		super(message, cause);
	}
}

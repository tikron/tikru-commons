/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikru.commons.util.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Base class for all my messagers
 *
 * @date 08.05.2019
 * @author Titus Kruse
 */
public abstract class BaseMessager implements Messager {

	@Override
	public abstract void notify(String message, String subject) throws MessagingException; 

	/**
	 * Composes an exception as human readable message. 
	 * 
	 * @param t Any {@link Throwable}.
	 * 
	 * @return The message text.
	 */
	public void notify(Throwable t) throws IOException, MessagingException {
		try (StringWriter writer = new StringWriter()) {
			try (PrintWriter printer = new PrintWriter(writer)) {
				printer.println("Exception:");
				printer.println();
				t.printStackTrace(printer);
			}
			notify(writer.toString(), "Exception occurred!");
		}
	}

}

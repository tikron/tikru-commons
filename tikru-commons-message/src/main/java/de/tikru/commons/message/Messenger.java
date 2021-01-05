/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.message;

import java.io.IOException;

/**
 * Declares common methods for messengers.
 *
 * @date 24.06.2015
 * @author Titus Kruse
 */
public interface Messenger {
	
	public void notify(String message, String subject) throws MessagingException;
	
	public void notify(Throwable t) throws IOException, MessagingException;

}

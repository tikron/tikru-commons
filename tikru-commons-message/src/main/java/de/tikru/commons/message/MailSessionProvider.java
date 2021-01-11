/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikru.commons.message;

import java.io.IOException;

import javax.mail.Session;

import de.tikru.commons.message.config.Authentication;

/**
 * 
 *
 * @author Titus Kruse
 * @since 05.05.2019
 */
public interface MailSessionProvider<T extends Authentication> {
	
	public Session provide(String hostname, int port, T auth) throws IOException;

}

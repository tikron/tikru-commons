/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikru.commons.util.message;

import java.io.IOException;

import javax.mail.Session;

import de.tikru.commons.util.config.xml.messenger.Authentication;

/**
 * 
 *
 * @date 05.05.2019
 * @author Titus Kruse
 */
public interface MailSessionProvider<T extends Authentication> {
	
	public Session provide(String hostname, int port, T auth) throws IOException;

}

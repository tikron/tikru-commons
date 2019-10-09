/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikron.common.message;

import java.io.IOException;

import javax.mail.Session;

import de.tikron.common.config.xml.messager.Authentication;

/**
 * 
 *
 * @date 05.05.2019
 * @author Titus Kruse
 */
public interface MailSessionProvider<T extends Authentication> {
	
	public Session provide(String hostname, int port, T auth) throws IOException;

}

/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikru.commons.message;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Session;

/**
 * 
 *
 * @author Titus Kruse
 * @since 06.05.2019
 */
public class DefaultMailSessionProvider {

	public Session provide(String hostname, int port) throws IOException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", hostname);
		props.setProperty("mail.smtp.port", Integer.toString(port));
//		props.setProperty("mail.smtp.starttls.enable", "true");
//		props.setProperty("mail.smtp.auth", "true");
//		props.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));
//		// props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.setProperty("mail.smtp.socketFactory.fallback", "false");

		return Session.getInstance(props);
	}
}

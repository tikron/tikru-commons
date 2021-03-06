/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikru.commons.message;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Session;

import de.tikru.commons.message.config.PasswordAuthentication;

/**
 * 
 *
 * @author Titus Kruse
 * @since 06.05.2019
 */
public class DefaultMailSessionProvider implements MailSessionProvider<PasswordAuthentication> {

	@Override
	public Session provide(String hostname, int port, PasswordAuthentication auth) throws IOException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", hostname);
		props.setProperty("mail.smtp.port", Integer.toString(port));
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));
		// props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");

		return Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(auth.getUsername(), auth.getPassword());
			}
		});
	}
}

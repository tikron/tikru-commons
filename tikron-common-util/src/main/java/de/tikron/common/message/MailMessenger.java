/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.common.message;

import java.io.IOException;
import java.nio.file.Path;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.tikron.common.config.xml.messenger.MailMessengerProperties;
import de.tikron.common.config.xml.messenger.OAuth2Authentication;
import de.tikron.common.config.xml.messenger.PasswordAuthentication;

/**
 * A {@link Messenger} sending an email.
 *
 * @date 24.06.2015
 * @author Titus Kruse
 */
public class MailMessenger extends BaseMessenger {
	
	private final MailMessengerProperties properties;
	
	private final Path workDirectory; 
	
	public MailMessenger(MailMessengerProperties properties, Path workDirectory) {
		this.properties = properties;
		this.workDirectory = workDirectory;
	}

	@Override
	public void notify(String message, String subject) throws MessengerException {
		try {
			Session session;
			if (properties.getAuthentication() instanceof PasswordAuthentication) {
				PasswordAuthentication authentication = (PasswordAuthentication) properties.getAuthentication();
				session = new DefaultMailSessionProvider().provide(properties.getHostname(), properties.getPort(), authentication);
			} else if (properties.getAuthentication() instanceof OAuth2Authentication) {
				OAuth2Authentication authentication = (OAuth2Authentication) properties.getAuthentication();
				session = new GoogleMailSessionProvider(workDirectory).provide(properties.getHostname(), properties.getPort(), authentication);
			} else {
				throw new IllegalArgumentException("Unsupported or missing smtp authentication type.");
			}
			if (properties.isDebug()) {
				session.setDebug(true);
			}
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(properties.getFrom(), "Polly"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(properties.getTo()));
			msg.setSubject(String.format("[Polly] %s", subject));
			msg.setText(message);

			Transport.send(msg);
			
		} catch (MessagingException e) {
			throw new MessengerException(e);
		} catch (IOException e) {
			throw new MessengerException(e);
		}
	}

	public MailMessengerProperties getProperties() {
		return properties;
	}
}

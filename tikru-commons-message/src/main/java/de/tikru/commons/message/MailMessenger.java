/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.message;

import java.io.IOException;
import java.nio.file.Path;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;

import de.tikru.commons.message.config.MailMessengerConfiguration;
import de.tikru.commons.message.config.OAuth2Authentication;
import de.tikru.commons.message.config.PasswordAuthentication;

/**
 * A {@link Messenger} sending an email.
 *
 * @author Titus Kruse
 * @since 24.06.2015
 */
public class MailMessenger extends BaseMessenger {
	
	private final MailMessengerConfiguration properties;
	
	private final Path workDirectory; 
	
	public MailMessenger(MailMessengerConfiguration properties, Path workDirectory) {
		this.properties = properties;
		this.workDirectory = workDirectory;
	}

	@Override
	public void notify(String message, String subject) throws MessagingException {
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
			msg.setFrom(properties.getFrom());
			msg.setRecipient(Message.RecipientType.TO, properties.getTo());
			msg.setSubject(subject);
			msg.setText(message);

			Transport.send(msg);
			
		} catch (javax.mail.MessagingException e) {
			throw new MessagingException(e);
		} catch (IOException e) {
			throw new MessagingException(e);
		}
	}

	public MailMessengerConfiguration getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("host", properties.getHostname()).append("to", properties.getTo()).build();
	}
}

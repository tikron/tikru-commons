/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.util.message;

import java.io.IOException;
import java.nio.file.Path;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.tikru.commons.util.config.xml.messager.MailMessagerProperties;
import de.tikru.commons.util.config.xml.messager.OAuth2Authentication;
import de.tikru.commons.util.config.xml.messager.PasswordAuthentication;

/**
 * A {@link Messager} sending an email.
 *
 * @date 24.06.2015
 * @author Titus Kruse
 */
public class MailMessager extends BaseMessager {
	
	private final MailMessagerProperties properties;
	
	private final Path workDirectory; 
	
	public MailMessager(MailMessagerProperties properties, Path workDirectory) {
		this.properties = properties;
		this.workDirectory = workDirectory;
	}

	@Override
	public void notify(String message, String subject) throws MessagerException {
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
			
		} catch (MessagingException e) {
			throw new MessagerException(e);
		} catch (IOException e) {
			throw new MessagerException(e);
		}
	}

	public MailMessagerProperties getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("host", properties.getHostname()).append("to", properties.getTo()).build();
	}
}

/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.message;

import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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
	
	private final MailMessengerConfiguration config;
	
	private final Path workDirectory;
	
	private Authenticator authenticator; 

	private static final String ACCESS_TOKEN_FILE_NAME = "google-access-token.json";
	
	public MailMessenger(MailMessengerConfiguration config, Path workDirectory) {
		this.config = config;
		this.workDirectory = workDirectory;
		
		if (config.getAuthentication() != null) {
			if (config.getAuthentication() instanceof PasswordAuthentication) {
				authenticator = PasswordAuthenticator.getInstance((PasswordAuthentication) config.getAuthentication());
			} else if (config.getAuthentication() instanceof OAuth2Authentication) {
				File tokenStore = workDirectory.resolve(ACCESS_TOKEN_FILE_NAME).toFile();
				authenticator = OAuth2Authenticator.getInstance((OAuth2Authentication) config.getAuthentication(), tokenStore);
			} else {
				throw new IllegalArgumentException("Unsupported smtp authentication type.");
			}
		}
	}

	@Override
	public void notify(String message, String subject) throws MessagingException {
		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", config.getHostname());
			if (config.getPort() > 0) {
				props.setProperty("mail.smtp.port", Integer.toString(config.getPort()));
			} else {
				props.setProperty("mail.smtp.port", Integer.toString(25));
			}
			// https://stackoverflow.com/questions/411331/using-javamail-with-tls
			if (config.isStartTLS()) {
				props.setProperty("mail.smtp.starttls.enable", "true");
				props.setProperty("mail.smtp.ssl.trust", config.getHostname());
			}
			if (config.getAuthentication() != null) {
				props.setProperty("mail.smtp.auth", "true");
				if (config.getAuthentication() instanceof OAuth2Authentication) {
					props.setProperty("mail.smtp.auth.mechanisms", "XOAUTH2");
				}
			}
			if (config.isDebug()) {
				props.setProperty("mail.debug", "true");
			}
			Session session = Session.getInstance(props, authenticator);

			try {
			  Message msg = new MimeMessage(session);
				msg.setFrom(config.getFrom());
				msg.setRecipient(Message.RecipientType.TO, config.getTo());
				msg.setSubject(subject);
				msg.setText(message);
			  Transport.send(msg);
			} catch (AddressException e) {
			  // ...
			} catch (MessagingException e) {
			  // ...
			}
			
		} catch (javax.mail.MessagingException e) {
			throw new MessagingException(e);
//		} catch (IOException e) {
//			throw new MessagingException(e);
		}
	}

	public MailMessengerConfiguration getConfig() {
		return config;
	}

	public Path getWorkDirectory() {
		return workDirectory;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("host", config.getHostname()).append("to", config.getTo()).build();
	}
}

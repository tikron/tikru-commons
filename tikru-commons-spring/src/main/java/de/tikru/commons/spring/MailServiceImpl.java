/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikru.commons.spring;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Stellt Funktionen zum Versenden von E-Mails bereit (Standardimplementierung).
 *
 * @date 23.12.2009
 * @author Titus Kruse
 */
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;

	private final String senderEmail;

	private final String recipientEmail;
	
	private boolean enabled;

	public MailServiceImpl(JavaMailSender mailSender, String senderEmail, String recipientEmail) {
		this.mailSender = Objects.requireNonNull(mailSender, "Constructor argument mailSender must not be null");
		this.senderEmail = Objects.requireNonNull(senderEmail, "Constructor argument senderEmail must not be null");
		this.recipientEmail = Objects.requireNonNull(recipientEmail, "Constructor argument recipientEmail must not be null");
	}

	public boolean send(String subject, String content) {
		return send(null, subject, content);
	}

	public boolean send(String recipientEmail, String subject, String content) {
		return send(null, recipientEmail, subject, content);
	}

	public boolean send(String senderEmail, String recipientEmail, String subject, String content) {
		return send(senderEmail, null, recipientEmail, subject, content);
	}

	public boolean send(String senderEmail, String senderName, String recipientEmail, String subject, String content) {
		Objects.requireNonNull(subject, "Missing parameter subject");
		Objects.requireNonNull(content, "Missing parameter content");
		try {
			if (enabled) {
		     MimeMessage message = mailSender.createMimeMessage();
		      
		     MimeMessageHelper helper = new MimeMessageHelper(message, true);
		      
		     // From
		     helper.setFrom(getSenderEmail());
		     // Reply to
		     if (senderEmail != null && senderName != null) {
			     helper.setReplyTo(senderEmail, senderName);
		     } else if (senderEmail != null) {
			     helper.setReplyTo(senderEmail);
		     }
		     // To
		     if (recipientEmail != null) {
			     helper.setTo(recipientEmail);
		     } else {
			     helper.setTo(getRecipientEmail());
		     }
				// Subject
				helper.setSubject(subject);
				// Message body
				helper.setText(content);
		     
				// Subject
				message.setSubject(subject);
				// Message body
				message.setText(content);
				
	      mailSender.send(message);
			}
		} catch (MailException | MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	@Override
	public String getSenderEmail() {
		return senderEmail;
	}

	@Override
	public String getRecipientEmail() {
		return recipientEmail;
	}
}

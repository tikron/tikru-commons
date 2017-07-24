/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.common.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Stellt Funktionen zum Versenden von E-Mails bereit (Standardimplementierung).
 *
 * @date 23.12.2009
 * @author Titus Kruse
 */
public class EmailServiceImpl implements EmailService {

	private JavaMailSender mailSender;
	
	private boolean enabled;

	private String sender;

	private String recipient;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.freakworm.common.service.EmailService#sendEmail(java.lang.String, java.lang.String)
	 */
	public boolean sendEmail(String subject, String content) {
		return sendEmail(null, subject, content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.freakworm.common.service.EmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean sendEmail(String recipient, String subject, String content) {
		return sendEmail(null, recipient, subject, content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.freakworm.common.service.EmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public boolean sendEmail(String sender, String recipient, String subject, String content) {
		return sendEmail(sender, null, recipient, subject, content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.freakworm.common.service.EmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public boolean sendEmail(String sender, String senderName, String recipient, String subject, String content) {
		try {
			if (enabled) {
				SimpleMailMessage message = new SimpleMailMessage();
				// Set sender address and name
				if (senderName != null)
					message.setFrom(senderName);
				else if (sender != null)
					message.setFrom(sender);
				else
					message.setFrom(this.sender);
				// Set recipient
				if (recipient != null)
					message.setTo(recipient);
				else
					message.setTo(this.recipient);
				// Set subject
				if (subject != null)
					message.setSubject(subject);
				else
					throw new IllegalArgumentException("Missing parameter subject");
				// Set message text
				if (content != null)
					message.setText(content);
				else
					throw new IllegalArgumentException("Missing parameter content");
				mailSender.send(message);
			}
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSender() {
		return sender;
	}

	@Required
	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	@Required
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}

/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.common.spring;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

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

	private String encoding;

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
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, false, encoding);
				// Set sender address and name
				if (senderName != null)
					helper.setFrom(sender, senderName);
				else if (sender != null)
					helper.setFrom(new InternetAddress(sender));
				else
					helper.setFrom(new InternetAddress(this.sender));
				// Set recipient
				if (recipient != null)
					helper.setTo(new InternetAddress(recipient));
				else
					helper.setTo(new InternetAddress(this.recipient));
				// Set subject
				if (subject != null)
					helper.setSubject(subject);
				else
					throw new IllegalArgumentException("Missing parameter subject");
				// Set message text
				if (content != null)
					helper.setText(content);
				else
					throw new IllegalArgumentException("Missing parameter content");
				mailSender.send(message);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
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

	@Required
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}

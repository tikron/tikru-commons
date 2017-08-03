/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.common.spring;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * Stellt Funktionen zum Versenden von E-Mails bereit (Standardimplementierung).
 *
 * @date 23.12.2009
 * @author Titus Kruse
 */
public class EmailServiceImpl implements EmailService {

	private JavaMailSender mailSender;

	private boolean enabled;

	private String senderEmail;

	private String recipientEmail;

	public boolean sendEmail(String subject, String content) {
		return sendEmail(null, subject, content);
	}

	public boolean sendEmail(String recipientEmail, String subject, String content) {
		return sendEmail(null, recipientEmail, subject, content);
	}

	public boolean sendEmail(String senderEmail, String recipientEmail, String subject, String content) {
		return sendEmail(senderEmail, null, recipientEmail, subject, content);
	}

	public boolean sendEmail(String senderEmail, String senderName, String recipientEmail, String subject, String content) {
		Objects.requireNonNull(subject, "Missing parameter subject");
		Objects.requireNonNull(content, "Missing parameter content");
		try {
			if (enabled) {
				MimeMessagePreparator mailMessage = new MimeMessagePreparator() {
				   public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
				     MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				     // Sender sender
				     if (senderEmail != null && senderName != null) {
				    	 message.setFrom(senderEmail, senderName);
				     } else if (senderEmail != null) {
				    	 message.setFrom(senderEmail);
				     } else {
				    	 message.setFrom(getSenderMail());
				     }
				     // Set recipient
				     if (recipientEmail != null) {
					     message.setTo(recipientEmail);
				     } else {
					     message.setTo(getRecipientEmail());
				     }
						// Set subject
						message.setSubject(subject);
						// Set message text
						message.setText(content);
				   }
				};
	      mailSender.send(mailMessage);
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

	public String getSenderMail() {
		return senderEmail;
	}

	@Required
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	@Required
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

}

/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import javax.mail.Authenticator;

import de.tikru.commons.message.config.PasswordAuthentication;

/**
	* 
	*
	* @author Titus Kruse
	* @since Nov 10, 2021
	*/
public class PasswordAuthenticator extends Authenticator {
	
	private final PasswordAuthentication properties;

	private PasswordAuthenticator(PasswordAuthentication properties) {
		this.properties = properties;
	}
	
	public static PasswordAuthenticator getInstance(PasswordAuthentication properties) {
		return new PasswordAuthenticator(properties);
	}

	@Override
	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication(properties.getUsername(), properties.getPassword());
	}

	public PasswordAuthentication getProperties() {
		return properties;
	}
}

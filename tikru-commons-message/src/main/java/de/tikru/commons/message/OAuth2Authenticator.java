/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import javax.mail.Authenticator;

/**
	* 
	*
	* @author Titus Kruse
	* @since Nov 10, 2021
	*/
public class OAuth2Authenticator extends Authenticator {
	
	private final String username;
	
	private final AccessTokenGenerator generator;
	
	private OAuth2Authenticator(String username, AccessTokenGenerator generator) {
		this.username = username;
		this.generator = generator;
	}

	public static OAuth2Authenticator getInstance(String username, AccessTokenGenerator generator) {
		return new OAuth2Authenticator(username, generator);
	}

	@Override
	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication(username, generator.getToken());
	}

	public String getUsername() {
		return username;
	}

	public AccessTokenGenerator getGenerator() {
		return generator;
	}
}

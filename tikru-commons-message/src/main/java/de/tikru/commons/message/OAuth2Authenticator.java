/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import java.io.File;

import javax.mail.Authenticator;

import de.tikru.commons.message.config.OAuth2Authentication;

/**
	* 
	*
	* @author Titus Kruse
	* @since Nov 10, 2021
	*/
public class OAuth2Authenticator extends Authenticator {
	
	private final OAuth2Authentication properties;
	
	private final GoogleAccessTokenGenerator generator;

	private OAuth2Authenticator(OAuth2Authentication properties, File tokenStore) {
		this.properties = properties;
		this.generator = new GoogleAccessTokenGenerator(tokenStore, properties.getClientId(), properties.getClientSecret(), properties.getRefreshToken());
	}
	
	public static OAuth2Authenticator getInstance(OAuth2Authentication properties, File tokenStore) {
		return new OAuth2Authenticator(properties, tokenStore);
	}

	@Override
	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication(properties.getUsername(), generator.getToken());
	}

	public OAuth2Authentication getProperties() {
		return properties;
	}
}

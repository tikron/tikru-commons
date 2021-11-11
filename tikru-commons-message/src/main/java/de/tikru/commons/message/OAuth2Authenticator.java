/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import java.io.IOException;

import javax.mail.Authenticator;

import de.tikru.commons.message.config.OAuth2Authentication;

/**
 * {@link javax.mail.Authenticator} with User Name and generated OAuth access token. An {@link AccessTokenStore} can be
 * passed to cache the tokens until they expired.
 * 
 * Note: The programmer is responsible to use the same file on disk for the each client in subsequent token generations.
 * 
 *
 * @author Titus Kruse
 * @since Nov 10, 2021
 */
public class OAuth2Authenticator extends Authenticator {
	
	private final OAuth2Authentication properties;

	private AccessTokenGenerator generator;

	private final AccessTokenStore tokenStore;

	public OAuth2Authenticator(OAuth2Authentication properties, AccessTokenStore tokenStore) {
		this.properties = properties;
		this.tokenStore = tokenStore;
		
		this.generator = new AccessTokenGeneratorFactory().create(properties.getTokenGenerator());
	}

	@Override
	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		try {
			AccessToken accessToken = null;
			// Restore cashed token
			if (tokenStore != null) {
				accessToken = tokenStore.restoreToken();
			}
			// get new token, if none cashed or cashed token is not valid anymore
			if (accessToken == null || accessToken.isExpired()) {
				accessToken = generator.getToken();
				if (tokenStore != null) {
					tokenStore.saveToken(accessToken);
				}
			}
			return new javax.mail.PasswordAuthentication(properties.getUsername(), accessToken.getToken());
		} catch (IOException e) {
			throw new MessagingException("IOException occured while generating OAuth access token.", e);
		}
	}

	public OAuth2Authentication getProperties() {
		return properties;
	}

	public AccessTokenGenerator getGenerator() {
		return generator;
	}

	public AccessTokenStore getTokenStore() {
		return tokenStore;
	}
}

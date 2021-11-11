/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message.config;

/**
	* POJO storing credentials for the Google OAuth2 API. 
	*
	* @author Titus Kruse
	* @since Nov 11, 2021
	*/
public class GoogleTokenGenerator extends TokenGenerator {

	private String clientId;
	
	private String clientSecret;
	
	private String refreshToken;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}

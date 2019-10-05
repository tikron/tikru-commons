package de.tikron.common.config.xml.messenger;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Copyright (c) 2019 by Titus Kruse.
 */

/**
 * Simple POJO holding smtp default authentication properties for OAuth2;
 *
 * @date 02.05.2019
 * @author Titus Kruse
 */
@XmlRootElement(name = "oauth2")
public class OAuth2Authentication extends Authentication {

	private String username;

	private String clientId;
	
	private String clientSecret;
	
	private String refreshToken;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
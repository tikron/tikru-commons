package de.tikru.commons.message.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Copyright (c) 2019 by Titus Kruse.
 */

/**
 * Simple POJO holding SMTP default authentication properties for OAuth2;
 *
 * @since 02.05.2019
 * @author Titus Kruse
 */
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("username", username).append("clientId", clientId).build();
	}
}

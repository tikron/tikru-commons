package de.tikru.commons.message.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Copyright (c) 2019 by Titus Kruse.
 */

/**
 * Simple POJO holding SMTP default authentication properties for OAuth2;
 *
 * @author Titus Kruse
 * @since 02.05.2019
 */
public class OAuth2Authentication extends Authentication {

	private String username;
	
	private TokenGenerator tokenGenerator;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public TokenGenerator getTokenGenerator() {
		return tokenGenerator;
	}

	public void setTokenGenerator(TokenGenerator tokenGenerator) {
		this.tokenGenerator = tokenGenerator;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("username", username).append("tokenGenerator", tokenGenerator).build();
	}
}

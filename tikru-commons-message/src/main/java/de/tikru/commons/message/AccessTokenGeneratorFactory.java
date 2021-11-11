/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import de.tikru.commons.message.config.GoogleTokenGenerator;
import de.tikru.commons.message.config.TokenGenerator;

/**
	* 
	*
	* @author Titus Kruse
	* @since Nov 11, 2021
	*/
public class AccessTokenGeneratorFactory {

	public AccessTokenGeneratorFactory() {
	}
	
	public AccessTokenGenerator create(TokenGenerator properties) {
		if (properties instanceof GoogleTokenGenerator) {
			GoogleTokenGenerator googleProps = (GoogleTokenGenerator) properties;
			return new GoogleAccessTokenGenerator(googleProps.getClientId(), googleProps.getClientSecret(), googleProps.getRefreshToken());
		} else {
			throw new IllegalArgumentException("Unsupported type of TokenGenerator: " + properties.getClass());
		}
	}
}

package de.tikru.commons.message;

public interface AccessTokenGenerator {

	/**
	 * Returns the current or possible new generated access token.
	 * 
	 * @return Access token string.
	 */
	String getToken();

}
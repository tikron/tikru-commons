package de.tikru.commons.message;

import java.io.IOException;

public interface AccessTokenGenerator {

	/**
	 * Returns the current or possible new generated access token.
	 * 
	 * @return Access token string.
	 * @throws IOException TODO
	 */
	AccessToken getToken() throws IOException;

}
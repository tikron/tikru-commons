package de.tikru.commons.message;

import java.io.IOException;

public interface AccessTokenStore {

	void saveToken(AccessToken accessToken) throws IOException;

	AccessToken restoreToken() throws IOException;

}
/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class generating an access token for Google client application OAuth2 authentication. The token will be hold in an instance of this class and renewed, if expired.
 * 
 * A file handle called token store can be passed to the generator to cache the token permanently.
 * 
 * Note: The programmer is responsible to use the same file on disk for the each client in subsequent token generations.
 *
 * @author Titus Kruse
 * @since Nov 10, 2021
 */
public class GoogleAccessTokenGenerator implements AccessTokenGenerator {
	
	private final File tokenStore;

	private final String clientId;

	private final String clientSecret;

	private final String refreshToken;

	private AccessToken accessToken;

	private static final String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";

	/**
	 * Initializes the token generator with token store.
	 * 
	 * @param tokenStore
	 * @param clientId
	 * @param clientSecret
	 * @param refreshToken
	 */
	public GoogleAccessTokenGenerator(File tokenStore, String clientId, String clientSecret, String refreshToken) {
		this.tokenStore = tokenStore;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.refreshToken = refreshToken;
	}

	/**
	 * Initializes the token generator without token store.
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @param refreshToken
	 */
	public GoogleAccessTokenGenerator(String clientId, String clientSecret, String refreshToken) {
		this.tokenStore = null;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.refreshToken = refreshToken;
	}

	/**
	 * Returns the current or possible new generated access token.
	 * 
	 * @return Access token string.
	 */
	@Override
	public String getToken() {
		// Restore cashed token
		if (accessToken == null && tokenStore != null) {
			accessToken = restoreToken();
		}
		// get new token, if none cashed or cashed token is not valid anymore 
		if (accessToken == null || accessToken.isExpired()) {
			accessToken = refreshToken();
			if (tokenStore != null) {
				saveToken(accessToken);
			}
		}
		return accessToken.getToken();
	}

	/**
	 * Calls the Google API to provide a new access token.
	 * 
	 * @return Google access token object.
	 */
	private AccessToken refreshToken() {
		try {
			// Prepare POST request body
			String request = "client_id=" + URLEncoder.encode(getClientId(), "UTF-8") + "&client_secret="
					+ URLEncoder.encode(getClientSecret(), "UTF-8") + "&refresh_token="
					+ URLEncoder.encode(getRefreshToken(), "UTF-8") + "&grant_type=refresh_token";
			// POST refresh
			HttpURLConnection connection = (HttpURLConnection) new URL(TOKEN_URL).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			PrintWriter writer = new PrintWriter(connection.getOutputStream());
			writer.print(request);
			writer.flush();
			writer.close();
			connection.connect();
			// Process response
			try {
				HashMap<String, Object> result = new ObjectMapper().readValue(connection.getInputStream(),
						new TypeReference<HashMap<String, Object>>() {
						});
				String accessToken = (String) result.get("access_token");
				int expiresIn = ((Number) result.get("expires_in")).intValue();
				return new AccessToken(accessToken, expiresIn);
			} catch (IOException e) {
				IOUtils.copy(connection.getErrorStream(), System.out);
				throw e;
			}
		} catch (IOException e) {
			throw new MessagingException("IOException occurred while refreshing OAuth access token:", e);
		}
	}

	private void saveToken(AccessToken accessToken) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(tokenStore, accessToken);
		} catch (IOException e) {
			throw new MessagingException("IOException occurred while saving OAuth access token:", e);
		}
	}

	private AccessToken restoreToken() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				return objectMapper.readValue(tokenStore, AccessToken.class);
			} catch (FileNotFoundException e) {
				return null;
			}
		} catch (IOException e) {
			throw new MessagingException("IOException occurred while restoring OAuth access token:", e);
		}
	}

	public File getTokenStore() {
		return tokenStore;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}

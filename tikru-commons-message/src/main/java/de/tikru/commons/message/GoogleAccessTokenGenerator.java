/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

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
 * Utility class generating an access token for Google client application OAuth2 authentication. The token will be hold
 * in an instance of this class and renewed, if expired.
 * 
 * The script "/tikru-commons-message/script/google-oauth2.py" should be used to request the refresh token required for generating access tokens. 
 *
 * @author Titus Kruse
 * @since Nov 10, 2021
 */
public class GoogleAccessTokenGenerator implements AccessTokenGenerator {

	private final String clientId;

	private final String clientSecret;

	private final String refreshToken;

	private static final String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";

	/**
	 * Initializes the token generator.
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @param refreshToken
	 */
	public GoogleAccessTokenGenerator(String clientId, String clientSecret, String refreshToken) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.refreshToken = refreshToken;
	}

	/**
	 * Returns the current or possible new generated access token. Calls the Google API to provide a new access token.
	 * 
	 * @return Access token string.
	 */
	@Override
	public AccessToken getToken() throws IOException {
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

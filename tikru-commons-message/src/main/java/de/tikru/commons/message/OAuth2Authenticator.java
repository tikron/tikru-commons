/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.HashMap;

import javax.mail.Authenticator;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tikru.commons.message.config.OAuth2Authentication;

/**
	* 
	*
	* @author Titus Kruse
	* @since Nov 10, 2021
	*/
public class OAuth2Authenticator extends Authenticator {
	
	private final OAuth2Authentication properties;
	
	private final Path credentialStore;
	
	private static final String ACCESS_TOKEN_FILE_NAME = "google-access-token.json";

	private static final String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
	
	private AccessToken accessToken;

	private OAuth2Authenticator(OAuth2Authentication properties, Path credentialStore) {
		this.properties = properties;
		this.credentialStore = credentialStore;
		this.accessToken = restoreToken();
	}
	
	public static OAuth2Authenticator getInstance(OAuth2Authentication properties, Path credentialStore) {
		return new OAuth2Authenticator(properties, credentialStore);
	}

	@Override
	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		if (accessToken == null) {
			accessToken = restoreToken();
		}
		if (accessToken == null || accessToken.isExpired()) {
			accessToken = refreshToken(properties);
			saveToken(accessToken);
		}
		return new javax.mail.PasswordAuthentication(properties.getUsername(), accessToken.getToken());
	}

	public OAuth2Authentication getProperties() {
		return properties;
	}
	
	public Path getCredentialStore() {
		return credentialStore;
	}

	/*
	 * Renew access token if expired
	 */
	private AccessToken refreshToken(OAuth2Authentication auth) {
		try {
			// Prepare POST request body
			String request = "client_id=" + URLEncoder.encode(auth.getClientId(), "UTF-8") + "&client_secret="
					+ URLEncoder.encode(auth.getClientSecret(), "UTF-8") + "&refresh_token=" + URLEncoder.encode(auth.getRefreshToken(), "UTF-8")
					+ "&grant_type=refresh_token";
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
			Path filePathName = credentialStore.resolve(ACCESS_TOKEN_FILE_NAME);
			objectMapper.writeValue(filePathName.toFile(), accessToken);
		} catch (IOException e) {
			throw new MessagingException("IOException occurred while saving OAuth access token:", e);
		}
	}
	
	private AccessToken restoreToken() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Path filePathName = credentialStore.resolve(ACCESS_TOKEN_FILE_NAME);
			try {
				return objectMapper.readValue(filePathName.toFile(), AccessToken.class);
			} catch (FileNotFoundException e) {
				return null;
			}
		} catch (IOException e) {
			throw new MessagingException("IOException occurred while restoring OAuth access token:", e);
		}
	}

	@SuppressWarnings("unused")
	private static class AccessToken {
		
		private String token;
		private long expires;
		
		/**
		 * Creates a new token with time to live.
		 * 
		 * @param token The token string.
		 * @param timeToLive Time to live in seconds.
		 */
		public AccessToken(String token, long timeToLive) {
			this.token = token;
			this.expires = System.currentTimeMillis() + (timeToLive * 1000);
		}

		public AccessToken() {
		}

		@JsonIgnore
		public boolean isExpired() {
			return System.currentTimeMillis() > expires;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public long getExpires() {
			return expires;
		}

		public void setExpires(long expires) {
			this.expires = expires;
		}
	}
}

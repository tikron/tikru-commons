/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikron.common.message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tikron.common.config.xml.messager.OAuth2Authentication;

/**
 * 
 *
 * @date 05.05.2019
 * @author Titus Kruse
 */
public class GoogleMailSessionProvider implements MailSessionProvider<OAuth2Authentication> {
	
	private static final String ACCESS_TOKEN_FILE_NAME = "google-access-token.json";

	private Path credentialStore;

	private static final String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";

	// private static String accessToken = "ya29.Glv-BrVfRAi6vIgHhCwtrY2BPAKxXAx7IW0ih-P7ijidGyzJ6Ml8Rj-893TgR1GoO3gkdPj3GdGRUsD1M_84AIdZsz3eEHDRtKFEAC-ZbMf1jMSSjWTizBEPNMqB";
	
	private AccessToken accessToken;

	public GoogleMailSessionProvider(Path credentialStore) throws IOException {
		this.credentialStore = credentialStore;
	}

	@Override
	public Session provide(String hostname, int port, OAuth2Authentication auth) throws IOException {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", hostname);
		props.setProperty("mail.smtp.port", Integer.toString(port));
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.auth.mechanisms", "XOAUTH2");

		if (accessToken == null) {
			accessToken = restoreToken();
		}
		if (accessToken == null || accessToken.isExpired()) {
			accessToken = refreshToken(auth);
			saveToken(accessToken);
		}

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(auth.getUsername(), accessToken.getToken());
			}
		});
		return session;
	}

	/*
	 * Renew access token if expired
	 */
	private AccessToken refreshToken(OAuth2Authentication auth) throws IOException {
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
	}
	
	private void saveToken(AccessToken accessToken) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Path filePathName = credentialStore.resolve(ACCESS_TOKEN_FILE_NAME);
		objectMapper.writeValue(filePathName.toFile(), accessToken);
	}
	
	private AccessToken restoreToken() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Path filePathName = credentialStore.resolve(ACCESS_TOKEN_FILE_NAME);
		try {
			return objectMapper.readValue(filePathName.toFile(), AccessToken.class);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public Path getCredentialStore() {
		return credentialStore;
	}

	public void setCredentialStore(Path credentialStore) {
		this.credentialStore = credentialStore;
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

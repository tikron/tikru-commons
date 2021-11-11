/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
	* {@link de.tikru.commons.message.AccessTokenStore} using the file system to store an {@link de.tikru.commons.message.AccessToken}.
	*
	* @author Titus Kruse
	* @since Nov 11, 2021
	*/
public class FileAccessTokenStore implements AccessTokenStore {
	
	private final File tokenStore;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	public FileAccessTokenStore(File file) {
		this.tokenStore = file;
	}

	public FileAccessTokenStore(Path path) {
		this.tokenStore = path.toFile();
	}

	public File getTokenStore() {
		return tokenStore;
	}

	@Override
	public void saveToken(AccessToken accessToken) throws IOException {
		objectMapper.writeValue(tokenStore, accessToken);
	}

	@Override
	public AccessToken restoreToken() throws IOException {
		try {
			return objectMapper.readValue(tokenStore, AccessToken.class);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}

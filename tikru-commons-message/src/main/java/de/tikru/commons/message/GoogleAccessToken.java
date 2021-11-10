package de.tikru.commons.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Simple data object to hold token string and expiration time stamp 
	* 
	*
	* @author Titus Kruse
	* @since Nov 10, 2021
 */
public class GoogleAccessToken {
	
	private String token;
	private long expires;
	
	/**
	 * Creates a new token with time to live.
	 * 
	 * @param token The token string.
	 * @param timeToLive Time to live in seconds.
	 */
	public GoogleAccessToken(String token, long timeToLive) {
		this.token = token;
		this.expires = System.currentTimeMillis() + (timeToLive * 1000);
	}

	public GoogleAccessToken() {
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
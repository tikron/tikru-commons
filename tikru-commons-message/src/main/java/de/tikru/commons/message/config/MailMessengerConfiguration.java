/**
	* Copyright (c) 2020 by Titus Kruse.
	*/

package de.tikru.commons.message.config;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
	* Messenger configuration for e-mail messaging.
	*
	* @author Titus Kruse
	* @since Dec 15, 2020
	*/

public class MailMessengerConfiguration extends MessengerConfiguration {

	private String hostname;

	private int port;

	private Authentication authentication;

	private boolean debug;

	private InternetAddress from;

	private InternetAddress to;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public InternetAddress getFrom() {
		return from;
	}

	public void setFrom(InternetAddress from) {
		this.from = from;
	}

	public InternetAddress getTo() {
		return to;
	}

	public void setTo(InternetAddress to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("hostname", hostname).append("port", port)
				.append("authentication", authentication).append("from", from).append("to", to).build();
	}
}

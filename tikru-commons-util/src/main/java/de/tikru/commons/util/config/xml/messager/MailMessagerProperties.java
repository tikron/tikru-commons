package de.tikru.commons.util.config.xml.messager;

import javax.mail.internet.InternetAddress;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.tikru.commons.util.config.xml.BaseElement;

/**
 * Copyright (c) 2011 by Titus Kruse.
 */

/**
 * Simple POJO holding the email properties;
 *
 * @date 08.08.2011
 * @author Titus Kruse
 */
public class MailMessagerProperties extends BaseElement {

	private String hostname;

	private int port;

	@XmlElements({ @XmlElement(name = "passwordAuth", type = PasswordAuthentication.class),
			@XmlElement(name = "oauth2", type = OAuth2Authentication.class) })
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

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
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

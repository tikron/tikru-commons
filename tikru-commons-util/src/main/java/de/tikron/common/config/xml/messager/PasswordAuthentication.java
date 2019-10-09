package de.tikron.common.config.xml.messager;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Copyright (c) 2019 by Titus Kruse.
 */

/**
 * Simple POJO holding smtp default authentication properties with user name and password;
 *
 * @date 02.05.2019
 * @author Titus Kruse
 */
@XmlRootElement(name = "passwordAuth")
public class PasswordAuthentication extends Authentication {

	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(ToStringStyle.SHORT_PREFIX_STYLE).append("username", username).build();
	}
}

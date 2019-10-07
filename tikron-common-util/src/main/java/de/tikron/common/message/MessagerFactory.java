/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.common.message;

import java.nio.file.Path;

import de.tikron.common.config.xml.messager.MessagerProperties;

/**
 * Constructs a {@link Messager} depending on type of message to send.
 *
 * @date 24.06.2015
 * @author Titus Kruse
 */
public class MessagerFactory {
	
	public static Messager create(MessagerProperties properties, Path workDirectory) {
		if (properties.getMailProperties() != null) {
			return new MailMessager(properties.getMailProperties(), workDirectory);
		} else { 
			throw new IllegalArgumentException("Unsupported or missing messager type.");
		}
	}
}

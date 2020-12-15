/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.util.message;

import java.nio.file.Path;

import de.tikru.commons.util.config.xml.messenger.MessengerProperties;

/**
 * Constructs a {@link Messenger} depending on type of message to send.
 *
 * @date 24.06.2015
 * @author Titus Kruse
 */
public class MessengerFactory {
	
	public static Messenger create(MessengerProperties properties, Path workDirectory) {
		if (properties.getMailProperties() != null) {
			return new MailMessenger(properties.getMailProperties(), workDirectory);
		} else { 
			throw new IllegalArgumentException("Unsupported or missing messenger type.");
		}
	}
}

/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.common.message;

import java.nio.file.Path;

import de.tikron.common.config.xml.messenger.MessengerProperties;

/**
 * Constructs a {@link Messenger} depending on type of message to send.
 *
 * @date 24.06.2015
 * @author Titus Kruse
 */
public class MessengerFactory {
	
	public static Messenger create(MessengerProperties messengerProperties, Path workDirectory) {
		if (messengerProperties.getMailProperties() != null) {
			return new MailMessenger(messengerProperties.getMailProperties(), workDirectory);
		} else { 
			throw new IllegalArgumentException("Unsupported or missing messenger type.");
		}
	}
}

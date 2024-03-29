/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.message;

import java.nio.file.Path;

import de.tikru.commons.message.config.MailMessengerConfiguration;
import de.tikru.commons.message.config.MessengerConfiguration;

/**
 * Constructs a {@link Messenger} depending on type of message to send.
 *
 * @author Titus Kruse
 * @since 24.06.2015
 */
public class MessengerFactory {
	
	public static Messenger create(MessengerConfiguration config, Path workDirectory) {
		if (config instanceof MailMessengerConfiguration) {
			return new MailMessenger((MailMessengerConfiguration) config, workDirectory);
		} else if (config == null) {
			return new DefaultMessenger();
		} else {
			throw new IllegalArgumentException("Unsupported type of MessengerConfiguration: " + config.getClass());
		}
	}
}

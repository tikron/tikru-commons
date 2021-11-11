/**
	* Copyright (c) 2021 by Titus Kruse.
	*/
package de.tikru.commons.message;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.tikru.commons.message.config.MessengerConfiguration;

/**
	* 
	*
	* @author Titus Kruse
	* @since Nov 11, 2021
	*/
public class MailMessengerTest {

	public static final Path WORK_DIRECTORY = Paths.get("/tmp");

	public static final File CONFIG_LOCAL_NETWORK = new File("config/messenger-local.yaml");

	public static final File CONFIG_POSTEO = new File("config/messenger-posteo.yaml");

	public static final File CONFIG_GOOGLE = new File("config/messenger-google.yaml");
	
	private static final String MSG_SUBJECT = "Test message sent by unit test.";
	
	private static final String MSG_TEXT = "Tikru test message";
	
	private static ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
	
	@Test
	public void messageShouldBeSendTolocalNetwork() throws IOException {
		MessengerConfiguration messengerConfig = yamlMapper.readValue(CONFIG_LOCAL_NETWORK, MessengerConfiguration.class);
		Messenger messenger = MessengerFactory.create(messengerConfig, WORK_DIRECTORY);
		messenger.notify(MSG_SUBJECT, MSG_TEXT);
	}
	
	@Test
	public void messageShouldBeSendToPosteo() throws IOException {
		MessengerConfiguration messengerConfig = yamlMapper.readValue(CONFIG_POSTEO, MessengerConfiguration.class);
		Messenger messenger = MessengerFactory.create(messengerConfig, WORK_DIRECTORY);
		messenger.notify(MSG_SUBJECT, MSG_TEXT);
	}
	
	@Test
	public void messageShouldBeSendToGoogle() throws IOException {
		MessengerConfiguration messengerConfig = yamlMapper.readValue(CONFIG_GOOGLE, MessengerConfiguration.class);
		Messenger messenger = MessengerFactory.create(messengerConfig, WORK_DIRECTORY);
		messenger.notify(MSG_SUBJECT, MSG_TEXT);
	}
}

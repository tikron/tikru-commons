/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * Base class providing access to a configuration file or resource.
 *
 * @date 15.07.2015
 * @author Titus Kruse
 */
//TODO Should be refactored into multiple stream providers
public abstract class ConfigurationManager {
	
	protected final String name;

	/**
	 * Constructor with name of file or resource.
	 * 
	 * @param name The name of the file or resource providing the configuation data.   
	 */
	protected ConfigurationManager(String name) {
		Objects.requireNonNull(name, "Configutation name must not be null.");
		this.name = name;
	}

	/**
	 * Tries to return an input stream to the configuration file or resource.
	 * 
	 * @return The input stream
	 * 
	 * @throws IllegalStateException If the configuration was not found.
	 */
	protected InputStream getStream() throws IllegalStateException {
		try {
			// Access file in the filesystem
			return new FileInputStream(new File(name));
		} catch (FileNotFoundException e) {
				throw new IllegalStateException(MessageFormat.format("configuration not found: {0}", name));
		}
	}

	public String getName() {
		return name;
	}

}

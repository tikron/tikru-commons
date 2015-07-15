/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.common;

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
public abstract class ConfigurationManager {
	
	protected String fileName;

	protected ConfigurationManager(String fileName) {
		Objects.requireNonNull(fileName, "Configutation file name must not be null.");
		this.fileName = fileName;
	}

	/**
	 * Tries to return an input stream to the configuration file.
	 * 
	 * @return The input stream
	 * 
	 * @throws IllegalStateException If the file was not found.
	 */
	protected InputStream getStream() throws IllegalStateException {
		try {
			// Access file in the filesystem
			return new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			// Access file relative to the current context
			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (stream == null) {
				throw new IllegalStateException(MessageFormat.format("configuration file not found: {0}",
						fileName));
			}
			return stream;
		}
	}

	public String getFileName() {
		return fileName;
	}

}

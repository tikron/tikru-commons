/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * A configuration manager fetching the configuration properties from a properties file.
 *
 * @date 13.07.2015
 * @author Titus Kruse
 */
public class PropertyFileConfigurationManager extends ConfigurationManager implements KeyValueConfigurationProvider {
	
	public static final String DEFAULT_RESOURCE_NAME = "configuration.properties";
	
	private Properties properties = new Properties();
	
	public PropertyFileConfigurationManager() throws IOException {
		this(DEFAULT_RESOURCE_NAME);
	}
	
	public PropertyFileConfigurationManager(String name) throws IOException {
		super(name);
		InputStream stream = null;
		try {
			stream = getStream();
			properties.load(stream);
		} finally {
			IOUtils.closeQuietly(stream);
		}
	}

	@Override
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

}

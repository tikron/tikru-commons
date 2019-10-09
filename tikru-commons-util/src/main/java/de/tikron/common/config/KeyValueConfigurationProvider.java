/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.config;

/**
 * A configuration manager provides access to the configuration on application level.
 *
 * @date 23.09.2011
 * @author Titus Kruse
 */
public interface KeyValueConfigurationProvider {

	/**
	 * Returnes a configuration value associated to the given key.
	 * 
	 * @param key The key.
	 * @return The value or null, if not found.
	 */
	public String getProperty(String key);

	/**
	 * Returnes a configuration value associated to the given key.
	 * 
	 * @param key The key.
	 * @param defaultValue A default value.
	 * @return The value or the default value, if not found.
	 */
	public String getProperty(String key, String defaultValue);

}

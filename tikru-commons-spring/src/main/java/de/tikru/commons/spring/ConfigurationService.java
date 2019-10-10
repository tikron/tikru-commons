/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikru.commons.spring;

import java.util.Properties;

/**
 * Liefert Basisfunktionen zum Konfigrieren der Anwendung.
 *
 * @date 23.12.2009
 * @author Titus Kruse
 */
public interface ConfigurationService {

	/**
	 * Liefert die Konfiguration der Anwendung.
	 * 
	 * @return Die Konfiguration der Anwendung.
	 */
	public abstract Properties getProperties();

	/**
	 * Liefert den Name der aktuellen Umgebung.
	 * 
	 * @return Die aktuelle Umgebung.
	 */
	public abstract String getEnvironment();

	/**
	 * Liefert einen Wert der Konfiguration.
	 * 
	 * @param key Der Schlüssel zum Wert.
	 * @return Der Wert oder null, falls er nicht gefunden wurde.
	 */
	public abstract String getProperty(String key);

	/**
	 * Liefert einen Wert der Konfiguration.
	 * 
	 * @param key Der Schlüssel zum Wert.
	 * @param defaultValue Ein Standardwert.
	 * @return Der Wert oder der Standardwert, falls der Schlüssel nicht gefunden wurde.
	 */
	public abstract String getProperty(String key, String defaultValue);

}

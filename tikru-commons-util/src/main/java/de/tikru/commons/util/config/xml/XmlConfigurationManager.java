/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikru.commons.util.config.xml;

import javax.xml.bind.JAXB;

import de.tikru.commons.util.config.ConfigurationManager;

/**
 * Loads configuration from a XML file and provides its properties.
 *
 * @date 08.08.2011
 * @author Titus Kruse
 */
public class XmlConfigurationManager<E extends BaseElement> extends ConfigurationManager {

	public static final String DEFAULT_FILENAME = "configuration.xml";
	
	private final Class<E> type;

	private E configuration = null;

	/**
	 * Instantiates this class.
	 * 
	 * @param type Type type of the configuration object to build from the resource.  
	 */
	public XmlConfigurationManager(Class<E> type) {
		this(type, DEFAULT_FILENAME);
	}

	/**
	 * Instantiates this class.
	 * 
	 * @param type Type type of the configuration object to build from the resource.
	 * @param name The name of the file or resource providing the configuation data.   
	 */
	public XmlConfigurationManager(Class<E> type, String name) {
		super(name);
		this.type = type;
	}

	/**
	 * Returns the configuration as object graph. The configuration will be loaded from a given resource on first call (lazy loaded).
	 * 
	 * @return The configuation as type E.
	 */
	public E get() {
		if (configuration == null) {
			configuration = JAXB.unmarshal(getStream(), type);
		}
		return configuration;
	}
}

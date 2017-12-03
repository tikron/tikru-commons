/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.common.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ServletContextAware;

/**
 * Liefert Basisfunktionen zum Konfigrieren der Anwendung. Die Konfiguration wird im Klassenpfad als XML-Datei mit dem
 * Namen configuration[.environment].xml erwartet. Dabei gibt [.environment] den Namen der Umgebung an. Der Name der
 * Umgebung wird im Anwendungskontext als Init-Parameter "environment" hinterlegt und kann so je Host oder
 * Tomcat-Instanz dynamisch vergeben werden.
 * 
 * @date 23.12.2009
 * @author Titus Kruse
 */
public class ConfigurationServiceImpl implements ConfigurationService, ServletContextAware {

	private static Logger logger = LogManager.getLogger();

	private ServletContext servletContext;

	private String environment;

	private Properties properties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.ConfigurationService#getCfg()
	 */
	@Override
	public Properties getProperties() {
		if (properties == null) {
			String environmentName = getEnvironment();
			String configurationFileName = getConfigFileName(environmentName);
			InputStream is = null;
			try {
				properties = new Properties();
				is = ConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(configurationFileName);
				if (is != null) {
					logger.info(String.format("Loading configuration from file '%s'.", configurationFileName));
					properties.loadFromXML(is);
				} else {
					throw new ConfigurationException("Configuration file not found: " + configurationFileName);
				}
			} catch (IOException e) {
				throw new ConfigurationException(e.getMessage());
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return properties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.ConfigurationService#getEnvironment()
	 */
	@Override
	public String getEnvironment() {
		if (environment == null) {
			environment = servletContext.getInitParameter("environment");
		}
		return environment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.ConfigurationService#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(String key) {
		return getProperties().getProperty(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.ConfigurationService#getProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getProperty(String key, String defaultValue) {
		return getProperties().getProperty(key, defaultValue);
	}

	/**
	 * Liefert den Namen der Konfigurationsdatei.
	 * 
	 * Der Name hat das Muster /configuration[.environment].xml
	 * 
	 * @param environment Der Name der Umgebung oder null, falls dem Namen keine Umgebung hinzugefügt werden soll.
	 * @return Der Name der Konfigurationsdatei.
	 */
	private String getConfigFileName(String environment) {
		StringBuffer fileName = new StringBuffer();
		fileName.append("/");
		fileName.append("configuration");
		if (environment != null) {
			fileName.append(".");
			fileName.append(environment);
		}
		fileName.append(".xml");
		return fileName.toString();
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * Wird ausgelöst, falls beim Laden der Konfiguration ein Fehler auftritt.
	 *
	 * @date 04.01.2009
	 * @author Titus Kruse
	 */
	public static class ConfigurationException extends RuntimeException {

		public ConfigurationException() {
			super();
		}

		public ConfigurationException(String message, Throwable cause) {
			super(message, cause);
		}

		public ConfigurationException(String message) {
			super(message);
		}

		public ConfigurationException(Throwable cause) {
			super(cause);
		}

	}

}

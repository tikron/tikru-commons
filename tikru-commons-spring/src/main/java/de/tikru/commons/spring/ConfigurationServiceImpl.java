/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikru.commons.spring;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

/**
 * Liefert Basisfunktionen zum Konfigrieren der Anwendung. Die Konfiguration wird im Klassenpfad als XML-Datei mit dem
 * Namen configuration[.environment].xml erwartet. Dabei gibt [.environment] den Namen der Umgebung an. Der Name der
 * Umgebung wird im Anwendungskontext als Init-Parameter "environment" hinterlegt und kann so je Host oder
 * Tomcat-Instanz dynamisch vergeben werden.
 * 
 * @author Titus Kruse
 * @since 23.12.2009
 */
public class ConfigurationServiceImpl implements ConfigurationService, ServletContextAware {

	private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	private ServletContext servletContext;

	private String environment;

	private Properties properties;

	@Override
	public Properties getProperties() {
		if (properties == null) {
			String environmentName = getEnvironment();
			String configurationFileName = getConfigFileName(environmentName);
			try (InputStream is = ConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(configurationFileName)) {
					logger.info(String.format("Loading configuration from file '%s'.", configurationFileName));
					properties = new Properties();
					properties.loadFromXML(is);
			} catch (FileNotFoundException e) {
				throw new ConfigurationException("Configuration file not found: " + configurationFileName);
			} catch (IOException e) {
				throw new ConfigurationException(e.getMessage());
			}
		}
		return properties;
	}

	@Override
	public String getEnvironment() {
		if (environment == null) {
			environment = servletContext.getInitParameter("environment");
		}
		return environment;
	}

	@Override
	public String getProperty(String key) {
		return getProperties().getProperty(key);
	}

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
	 * @since 04.01.2009
	 * @author Titus Kruse
	 */
	public static class ConfigurationException extends RuntimeException {

		private static final long serialVersionUID = 3167890693572219743L;

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

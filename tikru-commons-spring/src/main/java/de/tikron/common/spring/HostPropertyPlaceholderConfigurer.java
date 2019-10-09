package de.tikron.common.spring;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * HostPropertyPlaceholderConfigurer
 * 
 * Erweitert den PropertyPlaceholderConfigurer. Properties können durch spezielle Werte in Anhängigkeit vom Hostnamen
 * überschrieben werden. (Statt jedoch wie bei Jeff Dwyer den Hostname nur bei HOST zu ersetzen, wird er generell der
 * angefragten Property hinzufügt.)
 * 
 * @author Titus Kruse
 * @author Jeff Dwyer (blog) http://jdwyah.blogspot.com
 * 
 * @see http://jdwyah.blogspot.com/2006/12/updated-spring-configuration.html
 * 
 */
public class HostPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static Logger logger = LogManager.getLogger();

	private static String hostname;

	@Override
	protected String resolvePlaceholder(String placeholder, Properties props) {
		try {
			String key = placeholder + "." + getHostname();
			String value = props.getProperty(key);
			if (value == null) {
				value = props.getProperty(placeholder);
			}
			return value;
		} catch (UnknownHostException e) {
			logger.warn(e);
			return null;
		}
	}

	/**
	 * Liefert dem Hostname (lazy).
	 * 
	 * @throws UnknownHostException
	 */
	private String getHostname() throws UnknownHostException {
		if (hostname == null) {
			hostname = InetAddress.getLocalHost().getHostName().toLowerCase();
		}
		return hostname;
	}
}

/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikru.commons.faces.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Static class providing some utility methods.
 *
 * @author Titus Kruse
 * @since 24.02.2011
 */
public class FacesUtil {

	/**
	 * Returns the current faces view ID.
	 * 
	 * @return The current view ID.
	 */
	public static String getCurrentViewId() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}
	
	/**
	 * Builds an absolute URL from the Faces external HTTP context and a given file path.
	 * 
	 * @return The URL or null, if no URL can be constructed.
	 */
	public static URL buildURL(String file) {
		try {
			// The external context (Servlet or Portlet)
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			// Construct URL
			if (externalContext.getRequestServerPort() != -1) {
				return new URL(externalContext.getRequestScheme(), externalContext.getRequestServerName(), externalContext.getRequestServerPort(), file);
			} else {
				return new URL(externalContext.getRequestScheme(), externalContext.getRequestServerName(), file);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

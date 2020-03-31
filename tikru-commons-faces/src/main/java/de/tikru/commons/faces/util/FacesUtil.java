/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikru.commons.faces.util;

import java.net.URI;
import java.net.URISyntaxException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * Static class providing some utility methods.
 *
 * @date 24.02.2011
 * @author Titus Kruse
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
	 * Returns the context path of the current request as URI.
	 * 
	 * @return The URI or null, if no URI can be constucted.
	 */
	public static URI getContextURI() {
		try {
			// The external context (Servlet or Portlet)
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			// The web application context path
			String contextPath = ((ServletContext) externalContext.getContext()).getContextPath();
			// Construct URI
			return new URI(externalContext.getRequestScheme(), externalContext.getRequestServerName(), contextPath, null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns the server URI of the current request.
	 * 
	 * @return The URI or null, if no URI can be constucted.
	 */
	public static URI getServerURI() {
		try {
			// The external context (Servlet or Portlet)
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			// Construct URI
			return new URI(externalContext.getRequestScheme(), externalContext.getRequestServerName(), null, null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

}

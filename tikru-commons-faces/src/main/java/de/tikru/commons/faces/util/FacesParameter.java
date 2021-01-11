/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikru.commons.faces.util;

import java.text.MessageFormat;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Stellt Methoden bereit, die das Übertragen von Parametern zwischen Faces-Beans erleichtern. Die Methoden teilen sich
 * in Getter und Setter für jeden Scope auf.
 * 
 * @author Titus Kruse
 * @author BalusC (@see http://balusc.blogspot.com/2006/06/communication-in-jsf.html)
 */
public class FacesParameter {

	public static Object getRequestMapValue(String key) throws ParameterNotFoundException {
		Object value = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);
		if (value == null)
			throw new ParameterNotFoundException(key);
		return value;
	}

	public static Object getRequestMapValue(String key, Object defaultValue) {
		return ObjectUtils.defaultIfNull(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key),
				defaultValue);
	}

	public static void setRequestMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(key, value);
	}

	public static Object getFlashValue(String key) throws ParameterNotFoundException {
		Object value = FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key);
		if (value == null)
			throw new ParameterNotFoundException(key);
		return value;
	}

	public static Object getFlashValue(String key, Object defaultValue) {
		return ObjectUtils.defaultIfNull(FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key),
				defaultValue);
	}

	public static void setFlashValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put(key, value);
	}

	public static Object getSessionMapValue(String key) throws ParameterNotFoundException {
		Object value = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
		if (value == null)
			throw new ParameterNotFoundException(key);
		return value;
	}

	public static Object getSessionMapValue(String key, Object defaultValue) {
		return ObjectUtils.defaultIfNull(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key),
				defaultValue);
	}

	public static void setSessionMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
	}

	public static Object getApplicationMapValue(String key) throws ParameterNotFoundException {
		Object value = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
		if (value == null)
			throw new ParameterNotFoundException(key);
		return value;
	}

	public static Object getApplicationMapValue(String key, Object defaultValue) {
		return ObjectUtils.defaultIfNull(FacesContext.getCurrentInstance().getExternalContext().getApplicationMap()
				.get(key), defaultValue);
	}

	public static void setApplicationMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
	}

	// Special request parameter map getters

	public static Long getRequestParameterMapLongValue(String key) throws ParameterNotFoundException {
		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
		if (value == null)
			throw new ParameterNotFoundException(key);
		return Long.valueOf(value);
	}

	public static Long getRequestParameterMapLongValue(String key, Long defaultValue) {
		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
		if (value == null)
			return defaultValue;
		return Long.valueOf(value);
	}

	/**
	 * Exception thrown when a parameter was not found in the specific scope.
	 * 
	 * @since 24.02.2011
	 * @author Titus Kruse
	 */
	public static class ParameterNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 5883313816669044063L;

		public ParameterNotFoundException() {
			super();
		}

		public ParameterNotFoundException(String key) {
			super(MessageFormat.format("Missing parameter {0}.", key));
		}
	}
}

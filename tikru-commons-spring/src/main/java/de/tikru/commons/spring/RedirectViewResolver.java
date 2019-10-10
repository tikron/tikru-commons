/**
 * Copyright (c) 2013 by Titus Kruse.
 */
package de.tikru.commons.spring;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.RedirectView;

/**
 * View resolver providing redirectWithoutModel prefix to perform a redirect without model attributes as request
 * parameters.
 * 
 * @see http
 *      ://stackoverflow.com/questions/2163517/how-do-i-prevent-spring-3-0-mvc-modelattribute-variables-from-appearing
 *      -in-url
 * 
 * @date 02.01.2013
 * @author Titus
 */
public class RedirectViewResolver implements ViewResolver, Ordered {
	// Have a highest priority by default
	private int order = Integer.MIN_VALUE;

	// Uses this prefix to avoid interference with the default behaviour
	public static final String REDIRECT_URL_PREFIX = "redirectWithoutModel:";

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
			String redirectUrl = viewName.substring(REDIRECT_URL_PREFIX.length());
			return new RedirectView(redirectUrl, true, true, false);
		}
		return null;
	}

	@Override
	public int getOrder() {
		return order;
	}

}

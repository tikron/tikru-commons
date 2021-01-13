package de.tikru.commons.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Request wrapper and filter to prevent XSS attacks.
 * 
 * @see https://defensivecode.wordpress.com/2013/09/03/spring-security-xss/
 * 
 *      Alternatives:
 * 
 * @see https://defensivecode.wordpress.com/2013/09/03/spring-security-xss/
 * @see http://codehustler.org/blog/jersey-cross-site-scripting-xss-filter-for-java-web-apps/
 *
 * @author manavdewan
 * @since 11.12.2014
 */
public class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	}

}

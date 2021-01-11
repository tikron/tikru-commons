package de.tikru.commons.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Character set filter needed for UTF-8 encoding with Tomcat.
 * 
 * @see http://wiki.apache.org/tomcat/Tomcat/UTF-8
 *
 * @since 15.08.2009
 * @author Titus Kruse
 */
public class CharsetFilter implements Filter {

	private String encoding;

	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("requestEncoding");

		if (encoding == null)
			encoding = "UTF-8";
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException,
			ServletException {
		// Respect the client-specified character encoding
		// (see HTTP specification section 3.4.1)
		if (null == request.getCharacterEncoding())
			request.setCharacterEncoding(encoding);

		next.doFilter(request, response);
	}

	public void destroy() {
	}

}

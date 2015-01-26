/**
 * Copyright (c) 2010 by Titus Kruse.
 */
package de.tikron.common.spring.bytecontent;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Abstract Controller able to handle resources of any content type (image/jpeg, text/html, x-application/pdf).
 * 
 * @date 23.06.2010
 * @author Titus Kruse
 * @author horatiucd
 * @see http://imhoratiu.wordpress.com/2009/10/14/resolving-byte-content-views/
 */
public abstract class AbstractContentTypeController extends AbstractController {

	private String viewName;

	private String contentType;

	protected abstract byte[] getData(HttpServletRequest req) throws Exception;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		if (getContentType() == null)
			throw new IllegalArgumentException("contentType property must be set.");
		if (getViewName() == null)
			throw new IllegalArgumentException("viewName property must be set.");

		model.put("data", getData(req));
		model.put("contentType", getContentType());
		return new ModelAndView(getViewName(), model);
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}

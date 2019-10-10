/**
 * Copyright (c) 2010 by Titus Kruse.
 */
package de.tikru.commons.spring.bytecontent;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * Custom view extending from AbstractView and having the responsibility of taking byte data from the model and writing
 * it to the response.
 * 
 * @date 23.06.2010
 * @author Titus Kruse
 * @author horatiucd
 * @see http://imhoratiu.wordpress.com/2009/10/14/resolving-byte-content-views/
 */
public class ByteContentView extends AbstractView {

	@Override
	protected final void renderMergedOutputModel(@SuppressWarnings("rawtypes") Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		byte[] bytes = (byte[]) model.get("data");
		String contentType = (String) model.get("contentType");
		response.setContentType(contentType);
		response.setContentLength(bytes.length);

		ServletOutputStream out = response.getOutputStream();
		out.write(bytes);
		out.flush();
	}
}

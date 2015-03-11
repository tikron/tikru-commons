/**
 */
package de.tikron.common.spring.image;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Required;

/**
 * Image servlet displaying an image provided by an ImageService. The ImageService has to provide an image with the
 * given name and is responsible for server side scaling. The ImageService property should be configured by Spring. To
 * be used as Servlet in an appropriate <img> tag, add the following to web.xml:
 * 
 * <pre>
 * <servlet>
 *   <servlet-name>imageServlet</servlet-name>
 *   <servlet-class>de.tikron.common.spring.image.ImageServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 *   <servlet-name>imageServlet</servlet-name>
 *   <url-pattern>/imageServlet.jsp</url-pattern>
 * </servlet-mapping>
 * </pre>
 * 
 * Configure non Spring bean
 * 
 * <pre>
 * <bean class="de.tikron.common.spring.image.ImageServlet" scope="prototype">
 *   <property name="imageService" ref="imageService" />
 *   <property name="imageTemplateService" ref="imageTemplateService" />
 * </bean>
 * </pre>
 * 
 * An <img> tag on a xhtml page can look like that:
 * 
 * <pre>
 * <img src="imageServlet.jsp?name=myface.jpg&amp;width=100&amp;height=150" />
 * </pre>
 * 
 * @date 20.01.2011
 * @author Titus Kruse
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/04/imageservlet.html
 */
@Configurable(dependencyCheck = true)
public class ImageServlet extends HttpServlet {

	// Constants ----------------------------------------------------------------------------------

	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private static final long CACHE_INTERVAL = 3600; // 1 hour

	// Properties ---------------------------------------------------------------------------------

	private ImageService imageService;

	private ImageTemplateService imageTemplateService;

	// Actions ------------------------------------------------------------------------------------

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Get named and other properties from request.
			String imageName = request.getParameter("name");
			String templateName = request.getParameter("template");
			String width = request.getParameter("width");
			String height = request.getParameter("height");
			String limit = request.getParameter("limit");

			// Check if name is supplied to the request.
			if (imageName == null || imageName.length() == 0) {
				System.err.println("Missing parameter image name.");
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
				return;
			}

			// Check if width and height or limit are supplied to the request.
			if (isEmpty(templateName) && isEmpty(limit) && (isEmpty(width) || isEmpty(height))) {
				System.err.println("Missing parameter template, limit or width and height.");
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
				return;
			}

			// Get or create image properties
			ImageProperties properties = null;
			if (!isEmpty(templateName)) {
				properties = imageTemplateService.getTemplate(templateName);
				if (properties == null) {
					System.err.println(MessageFormat.format("Image template {0} not found.", templateName));
					response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
					return;
				}
			} else if (!isEmpty(width) && !isEmpty(height)) {
				properties = new ImageProperties(Integer.parseInt(width), Integer.parseInt(height));
			} else if (!isEmpty(limit)) {
				properties = new ImageProperties(Integer.parseInt(limit));
			}

			// Fetch image from image service
			InputStream imageStream = imageService.getImage(imageName, properties);

			// Image not found
			if (imageStream == null) {
				System.err.println(MessageFormat.format("Image {0} not found.", imageName));
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
				return;
			}

			// Get content type by filename.
			String contentType = getServletContext().getMimeType(imageName);

			// Init servlet response.
			response.reset();
			response.setBufferSize(DEFAULT_BUFFER_SIZE);
			response.setContentType(contentType);
			response.setHeader("Content-Length", String.valueOf(imageStream.available()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\"");

			// Set modification time and expiary duration
			final long cacheIntervalMillis = CACHE_INTERVAL * 1000;
			long now = System.currentTimeMillis();
			response.addHeader("Cache-Control", "max-age=" + CACHE_INTERVAL);
			response.addHeader("Cache-Control", "must-revalidate");// optional
			response.setDateHeader("Last-Modified", now);
			response.setDateHeader("Expires", now + cacheIntervalMillis);

			ServletOutputStream outputStream = response.getOutputStream();
			try {
				// Write file contents to response.
				IOUtils.copy(imageStream, outputStream);

				outputStream.flush();
			} finally {
				// Gently close streams.
				IOUtils.closeQuietly(outputStream);
				IOUtils.closeQuietly(imageStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method to check for a none exsiting or empty parameter.
	 * 
	 * @param value The parameter value.
	 * @return true, if null or length is 0.
	 */
	private boolean isEmpty(String value) {
		return value == null || value.length() == 0;
	}

	@Required
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@Required
	public void setImageTemplateService(ImageTemplateService imageTemplateService) {
		this.imageTemplateService = imageTemplateService;
	}

}

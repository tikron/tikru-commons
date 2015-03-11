/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.spring.image;

/**
 * The image template service.
 *
 * @date 01.09.2011
 * @author Titus Kruse
 */
public interface ImageTemplateService {

	/**
	 * Returnes the image template associated to the given name.
	 * 
	 * @param name The unique template name.
	 * @return The image template or null, if not found.
	 */
	public ImageProperties getTemplate(String name);

}

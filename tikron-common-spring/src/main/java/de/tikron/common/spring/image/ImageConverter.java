/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.spring.image;

import java.awt.image.BufferedImage;

/**
 * An image converter.
 *
 * @date 31.07.2011
 * @author Titus Kruse
 */
public interface ImageConverter {

	/**
	 * Converts an image according to properties of the given image tamplate.
	 * 
	 * @param image The source image.
	 * @param properties The image properties.
	 * @return The converted image.
	 */
	public BufferedImage convert(BufferedImage image, ImageProperties properties);

}

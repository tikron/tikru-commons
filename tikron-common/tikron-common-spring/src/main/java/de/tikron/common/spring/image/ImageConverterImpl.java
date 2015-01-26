/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.spring.image;

import java.awt.Font;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Service;

import de.tikron.common.ImageUtil;
import de.tikron.common.ImageUtil.ScaleMode;

/**
 * Default implementation of the image converter.
 *
 * @date 31.07.2011
 * @author Titus Kruse
 */
@Service("imageConverter")
public class ImageConverterImpl implements ImageConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.image.ImageConverter#convert(java.awt.image.BufferedImage,
	 * de.tikron.common.spring.image.ImageProperties)
	 */
	@Override
	public BufferedImage convert(BufferedImage image, ImageProperties properties) {
		BufferedImage targetImage = image;
		if (properties.getWidth() > 0 && properties.getHeight() > 0)
			targetImage = ImageUtil.getScaledImage(image, properties.getWidth(), properties.getHeight(), true);
		if (properties.getWidth() > 0)
			targetImage = ImageUtil.getScaledImage(image, ScaleMode.WIDTH, properties.getWidth(), true);
		if (properties.getHeight() > 0)
			targetImage = ImageUtil.getScaledImage(image, ScaleMode.HEIGHT, properties.getHeight(), true);
		else if (properties.getLimit() > 0)
			targetImage = ImageUtil.getScaledImage(image, ScaleMode.LIMIT, properties.getLimit(), true);
		if (properties.getSignature() != null)
			ImageUtil.addSignature(targetImage, properties.getSignature().getText(), new Font("Arial", Font.BOLD, properties
					.getSignature().getSize()), properties.getSignature().getOpacity(), properties.getSignature()
					.getMarginHorizontal(), properties.getSignature().getMarginVertical());
		return targetImage;
	}

}

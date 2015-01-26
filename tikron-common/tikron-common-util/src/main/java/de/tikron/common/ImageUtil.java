/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Hilfsmethoden für Bilder.
 *
 * @date 21.02.2009
 * @author Titus Kruse
 */
public class ImageUtil {

	public static enum ScaleMode {
		WIDTH, HEIGHT, BOTH, LIMIT
	};

	/**
	 * Convenience method that returns a scaled instance of the provided {@code BufferedImage}.
	 * 
	 * @see http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
	 * 
	 * @param img the original image to be scaled
	 * @param targetWidth the desired width of the scaled instance, in pixels
	 * @param targetHeight the desired height of the scaled instance, in pixels
	 * @param multiStep if true, this method will use a multi-step scaling technique that provides higher quality than the
	 *          usual one-step technique (only useful in downscaling cases, where {@code targetWidth} or
	 *          {@code targetHeight} is smaller than the original dimensions, and generally only when the {@code BILINEAR}
	 *          hint is specified)
	 * @return a scaled version of the original {@code BufferedImage}
	 */
	public static BufferedImage getScaledImage(BufferedImage img, int targetWidth, int targetHeight, boolean multiStep) {
		if (img == null)
			throw new IllegalArgumentException("Parameter img is null");
		if (targetWidth > img.getWidth())
			throw new IllegalArgumentException("Target width greater image width");
		if (targetHeight > img.getHeight())
			throw new IllegalArgumentException("Target height greater image height");
		// Begin scaling
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (multiStep) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (multiStep && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (multiStep && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			// Replaced by createCompatibleImage()
			// int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
			// : BufferedImage.TYPE_INT_ARGB;
			// BufferedImage tmp = new BufferedImage(w, h, type);
			BufferedImage tmp = img.createGraphics().getDeviceConfiguration().createCompatibleImage(w, h);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret.getGraphics().dispose();
			ret.flush();
			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

	/**
	 * Returns a scaled instance of the provided {@code BufferedImage} with a maximum target size.
	 * 
	 * @param img The source image.
	 * @param mode TODO
	 * @param value The size value.
	 * @param multiStep Scale by multi step.
	 * @return The scaled image.
	 */
	public static BufferedImage getScaledImage(BufferedImage img, ScaleMode mode, int value, boolean multiStep) {
		if (img == null)
			throw new IllegalArgumentException("Parameter img is null");
		int targetWidth = 0, targetHeight = 0;
		switch (mode) {
		case WIDTH:
			// Calculate height based an image size ratio
			targetWidth = value;
			targetHeight = img.getHeight() * value / img.getWidth();
			break;
		case HEIGHT:
			// Calculate width based an image size ratio
			targetWidth = img.getWidth() * value / img.getHeight();
			targetHeight = value;
			break;
		case BOTH:
			targetWidth = value;
			targetHeight = value;
			break;
		case LIMIT:
			// Calculate target height for horizontal orientated image and vice versa
			if (img.getWidth() >= img.getHeight() && img.getWidth() > value) {
				double ratio = ((double) img.getWidth()) / ((double) img.getHeight());
				targetWidth = value;
				targetHeight = (int) (targetWidth / ratio);
			} else if (img.getHeight() > img.getWidth() && img.getHeight() > value) {
				double ratio = ((double) img.getHeight()) / ((double) img.getWidth());
				targetHeight = value;
				targetWidth = (int) (targetHeight / ratio);
			} else {
				targetWidth = img.getWidth();
				targetHeight = img.getHeight();
			}
			break;
		default:
			throw new IllegalArgumentException("Parameter mode invalid");
		}
		return getScaledImage(img, targetWidth, targetHeight, multiStep);
	}

	/**
	 * Adds a signature text to the given image at the given position.
	 * 
	 * @param img The image.
	 * @param text The text to draw on the image.
	 * @param font The font to render the text.
	 * @param opacity The opacity.
	 * @param marginHorizontal A positive value for the margin from the left border of the image or a negative value for a
	 *          margin from the right border of the image.
	 * @param marginVertical A positive value for the margin from the top border of the image or a negative value for a
	 *          margin from the bottom border of the image.
	 */
	public static void addSignature(BufferedImage img, String text, Font font, float opacity, int marginHorizontal,
			int marginVertical) {
		Graphics2D g2d = (Graphics2D) img.getGraphics();
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
		g2d.setComposite(alpha);
		g2d.setColor(Color.white);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(font);
		FontMetrics fontMetrics = g2d.getFontMetrics();
		Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);
		int posX = marginHorizontal >= 0 ? marginHorizontal : Math.max(
				img.getWidth() - -marginHorizontal - (int) rect.getWidth(), 0);
		int posY = marginVertical >= 0 ? marginVertical + (int) rect.getHeight() : Math.max(img.getHeight()
				- -marginVertical, 0);
		g2d.drawString(text, posX, posY);
		g2d.dispose();
	}

	/**
	 * Rotates the given image by the given angle in degress.
	 * 
	 * @param img The image to rotate.
	 * @param angle The angle. Valid angles are from -90.0 to 90.0 degress.
	 * @return The rotated image.
	 */
	public static BufferedImage rotate(BufferedImage img, double angle) {
		int targetWidth, targetHeight, targetX, targetY;
		if (angle < -90.0 || angle > 90.0)
			throw new IllegalArgumentException("Parameter degress must be between -90.0 and 90.0.");
		// Swap orientation of target image, if angle is above 45 degress.
		if (angle >= 45.0 || angle <= -45.0) {
			targetWidth = img.getHeight();
			targetHeight = img.getWidth();
		} else {
			targetWidth = img.getWidth();
			targetHeight = img.getHeight();
		}
		// Calculate target image position depending on positive or negative angle.
		if (angle > 0.0) {
			targetX = 0;
			targetY = -targetWidth;
		} else if (angle < 0.0) {
			targetX = -targetHeight;
			targetY = 0;
		} else {
			targetX = 0;
			targetY = 0;
		}
		// Do rotation of image.
		BufferedImage rotatedImage = img.createGraphics().getDeviceConfiguration()
				.createCompatibleImage(targetWidth, targetHeight);
		Graphics2D g2d = (Graphics2D) rotatedImage.getGraphics();
		g2d.rotate(Math.toRadians(angle));
		g2d.drawImage(img, targetX, targetY, null);
		g2d.dispose();
		return rotatedImage;
	}

}

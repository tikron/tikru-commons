/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.spring.image;

/**
 * Image properties describing an image. Required properties are dimensions of width and height or a limiting size.
 * 
 * @date 31.07.2011
 * @author Titus Kruse
 */
public class ImageProperties {

	/**
	 * Width of the image in pixel.
	 */
	private int width;

	/**
	 * Height of the image in pixel.
	 */
	private int height;

	/**
	 * Maximum width and height in pixel.
	 */
	private int limit;

	/**
	 * A signature to draw over the image.
	 */
	private ImageSignature signature;

	public ImageProperties(int width, int height, ImageSignature signature) {
		this.width = width;
		this.height = height;
		this.signature = signature;
	}

	public ImageProperties(int width, int height) {
		this(width, height, null);
	}

	public ImageProperties(int limit, ImageSignature signature) {
		this.limit = limit;
		this.signature = signature;
	}

	public ImageProperties(int limit) {
		this(limit, null);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public ImageSignature getSignature() {
		return signature;
	}

	public void setSignature(ImageSignature signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "ImageProperties [width=" + width + ", height=" + height + ", limit=" + limit + ", " + signature + "]";
	}

}

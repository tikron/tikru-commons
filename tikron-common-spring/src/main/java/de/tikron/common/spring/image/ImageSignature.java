/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.spring.image;

/**
 * An image signature. Required property is the signature text. Optional properties are the font size, opacity,
 * horizontal and vertical margin.
 * 
 * @date 31.07.2011
 * @author Titus Kruse
 */
public class ImageSignature {

	public static final int DEFAULT_VERTICAL_MARGIN = 0;

	public static final int DEFAULT_HORIZONTAL_MARGIN = 0;

	public static final int DEFAULT_FONT_SIZE = 10;

	public static final float DEFAULT_OPACITY = 1.0f;

	/**
	 * The text to draw over the image.
	 */
	private String text;

	/**
	 * The font size of the text.
	 */
	private int size;

	/**
	 * The opacity of the signature.
	 */
	private float opacity;

	/**
	 * The horizontal margin to draw the text. A positive value is a margin from the left and a negative value is a margin
	 * from the right border of the image.
	 */
	private int marginHorizontal;

	/**
	 * The vertical margin to draw the text. A positive value is a margin from the top and a negative value is a margin
	 * from the bottom border of the image.
	 */
	private int marginVertical;

	public ImageSignature(String text, int size, int marginHorizontal, int marginVertical, float opacity) {
		this(text, size, opacity, marginHorizontal, marginVertical);
	}

	public ImageSignature(String text, int size, float opacity, int marginHorizontal, int marginVertical) {
		this.text = text;
		this.size = size;
		this.opacity = opacity;
		this.marginHorizontal = marginHorizontal;
		this.marginVertical = marginVertical;
	}

	public ImageSignature(String text, int size, int marginHorizontal, int marginVertical) {
		this(text, size, DEFAULT_OPACITY, marginHorizontal, marginVertical);
	}

	public ImageSignature(String text, int marginHorizontal, int marginVertical) {
		this(text, DEFAULT_FONT_SIZE, marginHorizontal, marginVertical);
	}

	public ImageSignature(String text, int size) {
		this(text, size, DEFAULT_HORIZONTAL_MARGIN, DEFAULT_VERTICAL_MARGIN);
	}

	public ImageSignature(String text) {
		this(text, DEFAULT_FONT_SIZE);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public int getMarginHorizontal() {
		return marginHorizontal;
	}

	public void setMarginHorizontal(int marginHorizontal) {
		this.marginHorizontal = marginHorizontal;
	}

	public int getMarginVertical() {
		return marginVertical;
	}

	public void setMarginVertical(int marginVertical) {
		this.marginVertical = marginVertical;
	}

	@Override
	public String toString() {
		return "ImageSignature [text=" + text + ", size=" + size + ", opacity=" + opacity + ", marginHorizontal="
				+ marginHorizontal + ", marginVertical=" + marginVertical + "]";
	}

}

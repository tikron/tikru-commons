/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.spring.image;

/**
 * An image template identifying image properties by a unique name.
 *
 * @date 01.09.2011
 * @author Titus Kruse
 */
public class ImageTemplate extends ImageProperties {

	/**
	 * The unique name identifying this image template.
	 */
	private String name;

	public ImageTemplate(String name, int width, int height, ImageSignature signature) {
		super(width, height, signature);
		this.name = name;
	}

	public ImageTemplate(String name, int width, int height) {
		super(width, height);
		this.name = name;
	}

	public ImageTemplate(String name, int limit, ImageSignature signature) {
		super(limit, signature);
		this.name = name;
	}

	public ImageTemplate(String name, int limit) {
		super(limit);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

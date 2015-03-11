package de.tikron.common.spring.image;

import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

	/**
	 * Adds a source image to the image store.
	 * 
	 * @param name The name of the image to identify.
	 * @param inputStream The InputStream providing the image data.
	 * 
	 * @throws IOException
	 */
	public void putSourceImage(String name, InputStream inputStream) throws IOException;

	/**
	 * Removes a source image from the image store.
	 * 
	 * @param name The name of the image to identify.
	 * 
	 * @throws IOException
	 */
	public void removeSourceImage(String name) throws IOException;

	/**
	 * Returnes the source image with the given name.
	 * 
	 * @param name The name of the image to return.
	 * @return The image data as InputStream.
	 * @throws IOException
	 */
	public InputStream getSourceImage(String name) throws IOException;

	/**
	 * Returnes an image with the given name scaled to width and height.
	 * 
	 * @param name The name of the image to return.
	 * @param properties The image properties specifying the image to return.
	 * @return The image data as InputStream.
	 * @throws IOException
	 */
	public InputStream getImage(String name, ImageProperties properties) throws IOException;

}

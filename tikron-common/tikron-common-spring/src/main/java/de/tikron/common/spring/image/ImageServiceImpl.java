/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.spring.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Default implementation of an ImageService.
 * 
 * @date 19.01.2011
 * @author Titus Kruse
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {

	private static Logger LOGGER = Logger.getLogger(ImageServiceImpl.class);

	private static final String ARCHIVE_NAME = "archive";

	private static final String CACHE_NAME = "cache";

	private ImageConverter imageConverter;

	private Resource basePath;

	@PostConstruct
	public void postConstruct() throws IOException {
		if (!basePath.exists())
			throw new FileNotFoundException(basePath.getDescription());
		if (!basePath.getFile().isDirectory())
			throw new RuntimeException("Base path is not a directory");
		if (!basePath.isReadable())
			throw new RuntimeException("Base path is not readable");
		// Create sub directories
		getArchiveResource().getFile().mkdirs();
		getCacheResource().getFile().mkdirs();
		LOGGER.info(MessageFormat.format("Image service initialized with base path: {0}",
				new Object[] { basePath.getDescription() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.image.ImageService#putSourceImage(java.lang.String, java.io.InputStream)
	 */
	@Override
	public void putSourceImage(String name, InputStream inputStream) throws IOException {
		File sourceFile = getArchiveFile(name);
		// Create subdirectories
		sourceFile.getParentFile().mkdirs();
		// Add/Update new image
		boolean exists = sourceFile.exists();
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(sourceFile);
			IOUtils.copy(inputStream, outputStream);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
		// Remove possible existing cached images
		if (exists) {
			removeCachedImages(name);
		}
		LOGGER.debug(MessageFormat.format("Source image {0} {1}.", new Object[] { name, exists ? "updated" : "added" }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.image.ImageService#removeSourceImage(java.lang.String)
	 */
	@Override
	public void removeSourceImage(String name) throws IOException {
		// Remove source image
		getArchiveFile(name).delete();
		// Delete cached images
		removeCachedImages(name);
		LOGGER.debug(MessageFormat.format("Source image {0} removed.", new Object[] { name }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.image.ImageService#getSourceImage(java.lang.String)
	 */
	@Override
	public InputStream getSourceImage(String name) throws IOException {
		File sourceFile = getArchiveFile(name);
		if (sourceFile.exists()) {
			LOGGER.debug(MessageFormat.format("Delivering source image {0}.", new Object[] { name }));
			return new FileInputStream(sourceFile);
		} else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.image.ImageService#getImage(java.lang.String,
	 * de.tikron.common.spring.image.ImageProperties)
	 */
	@Override
	public InputStream getImage(String name, ImageProperties properties) throws IOException {
		InputStream inputStream = null;
		// Check whether the original (full size) image exists.
		File sourceFile = getArchiveFile(name);
		if (sourceFile.exists()) {
			// Generate the file name for the cached image
			String cachedName = getCacheFileName(name, properties);
			// Try to fetch scaled image from disk
			File cachedFile = getCacheFile(cachedName);
			if (cachedFile.exists() && cachedFile.lastModified() >= sourceFile.lastModified()) {
				// Return cached scaled image, if exists and original image is not updated since cached image has been stored.
				inputStream = new FileInputStream(cachedFile);
				LOGGER.debug(MessageFormat.format("Delivering cached image {0} for properties {1}.", new Object[] { name,
						properties }));
			} else {
				// Otherwise scale image and cache to disk
				BufferedImage sourceImage = ImageIO.read(sourceFile);
				BufferedImage scaledImage = this.imageConverter.convert(sourceImage, properties);
				// Create subdirectories
				cachedFile.getParentFile().mkdirs();
				// Create image depending on file type like JPEG
				String nameExtension = FilenameUtils.getExtension(name);
				// Write scaled image to disk and return input stream to it.
				ImageIO.write(scaledImage, nameExtension, cachedFile);
				// Remove images from memory
				// scaledImage.getGraphics().dispose();
				scaledImage.flush();
				// sourceImage.getGraphics().dispose();
				sourceImage.flush();
				// Return input stream to scaled image
				inputStream = new FileInputStream(cachedFile);
				LOGGER.debug(MessageFormat.format("Delivering new image {0} for properties {1}.", new Object[] { name,
						properties }));
			}
		}
		return inputStream;
	}

	/**
	 * Returnes the file name of a cached image for the given properties. The unique name will be generated based on the
	 * properties properties.
	 * 
	 * @param fileName The source file name.
	 * @param properties The image properties.
	 * @return The name of the cached file.
	 */
	private String getCacheFileName(String fileName, ImageProperties properties) {
		StringBuffer result = new StringBuffer();
		result.append(FilenameUtils.getFullPath(fileName));
		result.append(FilenameUtils.getBaseName(fileName));
		if (properties.getWidth() != 0 || properties.getHeight() != 0)
			result.append(String.format("-w%05d-h%05d", properties.getWidth(), properties.getHeight()));
		if (properties.getLimit() != 0)
			result.append(String.format("-l%05d", properties.getLimit()));
		result.append(FilenameUtils.EXTENSION_SEPARATOR);
		result.append(FilenameUtils.getExtension(fileName));
		return result.toString();
	}

	/**
	 * Returnes a resource to the image archive.
	 * 
	 * @return The resource.
	 * @throws IOException
	 */
	private Resource getArchiveResource() throws IOException {
		return basePath.createRelative(ARCHIVE_NAME + "/");
	}

	/**
	 * Returnes a file handle to the source image with the given name.
	 * 
	 * @param name The name of the source image.
	 * @return The file handle.
	 * @throws IOException
	 */
	private File getArchiveFile(String name) throws IOException {
		return getArchiveResource().createRelative(name).getFile();
	}

	/**
	 * Returnes a resource to the image cache.
	 * 
	 * @return The resource.
	 * @throws IOException
	 */
	private Resource getCacheResource() throws IOException {
		return basePath.createRelative(CACHE_NAME + "/");
	}

	/**
	 * Returnes a file handle to the cached image with the given name.
	 * 
	 * @param name The name of the source image.
	 * @return The file handle.
	 * @throws IOException
	 */
	private File getCacheFile(String name) throws IOException {
		return getCacheResource().createRelative(name).getFile();
	}

	/**
	 * Removes all cached images associated to the given source image name.
	 * 
	 * @param name The source image name
	 * @throws IOException
	 */
	private void removeCachedImages(String name) throws IOException {
		File cachedImageDir = getCacheFile(name).getParentFile();
		String baseName = FilenameUtils.getBaseName(name);
		File files[] = cachedImageDir.listFiles((FileFilter) new PrefixFileFilter(baseName));
		for (File file : files) {
			file.delete();
		}
	}

	/**
	 * Sets the image converter to use for converting images.
	 * 
	 * @param imageConverter the imageConverter to set.
	 */
	@Required
	public void setImageConverter(ImageConverter imageConverter) {
		this.imageConverter = imageConverter;
	}

	/**
	 * Sets the base path used to store files.
	 * 
	 * @param basePath The basePath to set.
	 * @throws IOException
	 */
	@Required
	public void setBasePath(Resource basePath) {
		this.basePath = basePath;
	}

}

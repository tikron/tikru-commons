/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.common.spring.image;

import java.sql.Blob;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import de.tikron.common.ImageCache;

/**
 * Liefert einen Cache-Speicher für Bilder basierend auf dem ImageCache. Die Klasse dient als Wrapper für die Klasse
 * ImageCache. Statt auf das Dateisystem zuzugreifen, verwendet die Klasse Context-Pfade.
 * 
 * @date 21.02.2009
 * @author Titus Kruse
 */
@Deprecated
@Service("imageCacheService")
public class ImageCacheServiceImpl implements ServletContextAware, ImageCacheService {

	private ServletContext servletContext;

	private String cacheUrl;

	private ImageCache imageCache;

	@Override
	public ImageCache getImageCache() {
		if (imageCache == null) {
			String cachePath = servletContext.getRealPath(this.cacheUrl);
			imageCache = new ImageCache(cachePath);
		}
		return imageCache;
	}

	public void setImageCache(ImageCache imageCache) {
		this.imageCache = imageCache;
	}

	@Override
	public String getImage(String name, Blob blob) {
		String imageUrl = null;
		try {
			imageUrl = this.cacheUrl + "/" + getImageCache().getImage(name, blob);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageUrl;
	}

	@Override
	public String getImage(String name, Blob blob, int width, int height) {
		String imageUrl = null;
		try {
			imageUrl = this.cacheUrl + "/" + getImageCache().getImage(name, blob, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageUrl;
	}

	@Override
	public void deleteImages(String name) {
		getImageCache().deleteImages(name);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Required
	public void setCacheUrl(String cacheUrl) {
		this.cacheUrl = cacheUrl;
	}

}

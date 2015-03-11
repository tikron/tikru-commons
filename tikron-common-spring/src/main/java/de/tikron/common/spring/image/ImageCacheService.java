package de.tikron.common.spring.image;

import java.sql.Blob;

import de.tikron.common.ImageCache;

@Deprecated
public interface ImageCacheService {

	public ImageCache getImageCache();

	public String getImage(String name, Blob blob);

	public String getImage(String name, Blob blob, int width, int height);

	public void deleteImages(String name);

}

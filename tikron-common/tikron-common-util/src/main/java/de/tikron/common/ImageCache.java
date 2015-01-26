/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;

/**
 * Stellt einen Zischenspeicher für Bilder bereit. Ein Bild wird, falls es nicht bereits im Cache vorhanden ist, dort
 * gespeichert. Gegebenenfalls wird es vor dem Speuichern skaliert.
 * 
 * @date 21.02.2009
 * @author Titus Kruse
 */
public class ImageCache {

	private String cachePath;

	/**
	 * Instanziert den Image-Cache.
	 * 
	 * @param cachePath Der Verzeichnispfad, in dem die Bilder gespeichert werden.
	 */
	public ImageCache(String cachePath) {
		this.cachePath = cachePath;
	}

	/**
	 * Speicher ein als BLOB übergebenes Bild und liefert den Kontextpfadnamen zum Bild. Falls das Bild bereits vorhanden
	 * ist, wird nur der Name geliefert.
	 * 
	 * @param name Der Name (ohne Endung), unter dem die Bilddatei gespeichert bzw. aufgefunden wird.
	 * @param blob Das Bild als BLOB.
	 * @return Der Name der resultierenden Graphikdatei.
	 * @throws SQLException
	 * @throws IOException
	 */
	public String getImage(String name, Blob blob) throws IOException, SQLException {
		// Prüfen, ob die Bilddatei bereits vorhanden ist.
		File file = new File(getImagePath(name));
		if (!file.exists()) {
			if (blob != null) {
				// Bilddatei erzeugen
				OutputStream ostream = new FileOutputStream(getImagePath(name));
				ostream.write(blob.getBytes(1L, (int) blob.length()));
				ostream.flush();
				ostream.close();
			}
		}
		return getImageFileName(name);
	}

	/**
	 * Skaliert ein als BLOB übergebenes Bild, speichert dieses und liefert den Kontextpfadnamen zum Bild. Falls das Bild
	 * bereits vorhanden ist, wird nur der Name geliefert.
	 * 
	 * @param name Der Name (ohne Endung), unter dem die Bilddatei gespeichert bzw. aufgefunden wird.
	 * @param blob Das Bild als BLOB.
	 * @param width Die Breite, auf die das Bild skaliert wird.
	 * @param height Die Höhe, auf die das Bild skaliert wird.
	 * @return Der Name der resultierenden Graphikdatei.
	 * @throws SQLException
	 * @throws IOException
	 */
	public String getImage(String name, Blob blob, int width, int height) throws IOException, SQLException {
		// Name des skalierten Bildes aus der Bildgröße generieren
		String scaledName = String.format("%s-%05d-%05d", name, width, height);
		// Prüfen, ob die Bilddatei bereits vorhanden ist.
		File file = new File(getImagePath(scaledName));
		if (!file.exists()) {
			if (blob != null) {
				BufferedImage image = ImageIO.read(blob.getBinaryStream());
				BufferedImage bufferedImage = ImageUtil.getScaledImage(image, width, height, true);
				OutputStream ostream = new FileOutputStream(getImagePath(scaledName));
				ImageIO.write(bufferedImage, "jpg", ostream);
				ostream.flush();
				ostream.close();
			}
		}
		return getImageFileName(scaledName);
	}

	/**
	 * Löscht gespeicherte Bilder.
	 * 
	 * @param name Der Name des Bilder, dessen Cache-Einträge gelöscht werden.
	 */
	public void deleteImages(String name) {
		File imageCache = new File(getCachePath());
		File files[] = imageCache.listFiles(new PrefixFilenameFilter(name));
		for (File file : files) {
			file.delete();
		}
	}

	/**
	 * Liefert den Verzeichnispfad, in dem die Bilder gespeichert sind.
	 * 
	 * @return Der Verzeichnispfad.
	 */
	public String getCachePath() {
		return this.cachePath;
	}

	/**
	 * Liefert den Dateinamen eines Bildes
	 * 
	 * @return Der Dateiname.
	 */
	private String getImageFileName(String imageName) {
		return imageName + ".jpg";
	}

	/**
	 * Liefert den Verzeichnisnamen eines Bildes
	 * 
	 * @return Der Verzeichnisnamen.
	 */
	private String getImagePath(String imageName) {
		return getCachePath() + "/" + getImageFileName(imageName);
	}

	/**
	 * Ein FilenameFilter, der Dateien nach Präfix auswählt.
	 */
	private static class PrefixFilenameFilter implements FilenameFilter {

		String prefix;

		/**
		 * Erstellt den PrefixFilenameFilter unter Angabe des Präfixes.
		 * 
		 * @param prefix Das Präfix, nach dem die Dateien gefiltert werden.
		 */
		public PrefixFilenameFilter(String prefix) {
			this.prefix = prefix;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String name) {
			return new File(dir, name).isFile() && name.toLowerCase().startsWith(prefix);
		}
	}

}

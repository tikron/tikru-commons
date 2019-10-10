package de.tikru.commons.spring.bytecontent;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Required;

/**
 * Controller providing the byte data taken from a file.
 * 
 * @date 23.06.2010
 * @author Titus Kruse
 * @author horatiucd
 * @see http://imhoratiu.wordpress.com/2009/10/14/resolving-byte-content-views/
 */
public class FileContentController extends AbstractContentTypeController {

	private String filePath;

	@Override
	protected byte[] getData(HttpServletRequest req) throws Exception {
		File file = new File(getFilePath());
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

	public String getFilePath() {
		return filePath;
	}

	@Required
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}

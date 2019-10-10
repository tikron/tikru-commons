/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.util.message;

import java.io.IOException;

/**
 * Declares common methods for messagers.
 *
 * @date 24.06.2015
 * @author Titus Kruse
 */
public interface Messager {
	
	public void notify(String message, String subject) throws MessagerException;
	
	public void notify(Throwable t) throws IOException, MessagerException;

}

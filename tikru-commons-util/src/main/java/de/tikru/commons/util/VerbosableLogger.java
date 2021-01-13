/**
	* Copyright (c) 2020 by Titus Kruse.
	*/

package de.tikru.commons.util;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * Extends the {@link org.slf4j.Logger} with another logging level "fine". This level is finer then info but not debug.
 * Can be enhanced with finer and finest later on.
 *
 * @author Titus Kruse
 * @since Dec 20, 2020
 */

public interface VerbosableLogger extends Logger {

	public void fine(String msg);

	public void fine(String format, Object arg1, Object arg2);

	public void fine(String format, Object... arg);

	public void fine(String msg, Throwable t);

	public void fine(Marker marker, String msg);

	public void fine(Marker marker, String format, Object arg1, Object arg2);

	public void fine(Marker marker, String format, Object... arg);

	public void fine(Marker marker, String msg, Throwable t);

	public boolean isFineEnabled();
}

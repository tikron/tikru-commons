/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikron.common.thread;

import java.util.function.Supplier;

/**
 * Helper class for thread safe lazy initialization.
 * 
 * @see https://www.nosid.org/java8-threadsafe-lazy-initialization.html
 *
 * @date 05.10.2019
 * @author Hubert Schmid
 * @author Titus Kruse
 */
public final class Once<T> {
	private volatile boolean initialized = false;
	private T object = null;

	public T get(Supplier<T> supplier) {
		if (!initialized) {
			synchronized (this) {
				if (!initialized) {
					object = supplier.get();
					initialized = true;
				}
			}
		}
		return object;
	}
}
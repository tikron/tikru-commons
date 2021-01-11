/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikru.commons.util.thread;

import java.util.function.Supplier;

/**
 * Helper class for thread safe lazy initialization.
 * 
 * @see https://www.nosid.org/java8-threadsafe-lazy-initialization.html
 *
 * @author Hubert Schmid
 * @since 05.10.2019
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
/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikru.commons.util.thread;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Helper class for thread safe lazy initialization.
 * 
 * @see https://www.nosid.org/java8-threadsafe-lazy-initialization.html
 *
 * @since 05.10.2019
 * @author Hubert Schmid
 * @author Titus Kruse
 */
public final class Deferred<T> {
	private volatile Supplier<T> supplier = null;
	private T object = null;

	public Deferred(Supplier<T> supplier) {
		this.supplier = Objects.requireNonNull(supplier);
	}

	public T get() {
		if (supplier != null) {
			synchronized (this) {
				if (supplier != null) {
					object = supplier.get();
					supplier = null;
				}
			}
		}
		return object;
	}
}

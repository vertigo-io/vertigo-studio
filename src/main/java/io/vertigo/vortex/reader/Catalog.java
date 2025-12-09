package io.vertigo.vortex.reader;

import java.util.HashMap;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;

final class Catalog<K, E> {
	private final Map<K, E> map = new HashMap<>();

	void put(K key, E element) {
		Assertion.check()
				.isNotNull(key)
				.isNotNull(element);
		//---
		map.put(key, element);
	}

	E get(K key) {
		Assertion.check()
				.isNotNull(key);
		//---
		E element = map.get(key);
		if (element == null) {
			throw new VSystemException("No data found for key = '{0}'", key);
		}
		return element;
	}
}

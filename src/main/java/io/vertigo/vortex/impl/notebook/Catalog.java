package io.vertigo.vortex.impl.notebook;

import java.util.HashMap;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.vortex.notebook.VXKey;

final class Catalog<E> {
	private final Map<VXKey, E> map = new HashMap<>();

	void put(VXKey key, E element) {
		Assertion.check()
				.isNotNull(key)
				.isNotNull(element);
		//---
		map.put(key, element);
	}

	E get(VXKey key) {
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

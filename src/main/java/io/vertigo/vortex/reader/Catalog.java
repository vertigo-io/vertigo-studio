package io.vertigo.vortex.reader;

import java.util.HashMap;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;

final class Catalog<E> {
	private final Map<String, E> map = new HashMap<>();

	void put(String name, E element) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(element);
		//---
		map.put(name, element);
	}

	E get(String name) {
		Assertion.check()
				.isNotBlank(name);
		//---
		E element = map.get(name);
		if (element == null) {
			throw new VSystemException("No data found for name = '{0}'", name);
		}
		return element;
	}

}

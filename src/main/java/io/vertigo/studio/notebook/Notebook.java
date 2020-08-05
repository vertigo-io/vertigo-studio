/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2019, Vertigo.io, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.studio.notebook;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;

/**
 * A Studio notebook is a space where sketches are shared and modified. (not threadSafe).
 * It's a conceptual representation of a project design that is human readable, storable and comparable
 *, 
 * @author mlaroche, pchretien
 */
public final class Notebook {
	private final Map<SketchKey, Sketch> sketches = new LinkedHashMap<>();

	/**
	 * Registers a new Sketch.
	 * The sketch must not be already registered.
	 * @param sketch the sketch
	 */
	public void register(final Sketch sketch) {
		Assertion.check()
				.isNotNull(sketch)
				.isFalse(contains(sketch.getKey()), "this sketch '{0}' is already registered", sketch);
		//---
		sketches.put(sketch.getKey(), sketch);
	}

	/** {@inheritDoc} */
	public boolean contains(final SketchKey key) {
		Assertion.check().isNotNull(key);
		//---
		return sketches.containsKey(key);
	}

	/** {@inheritDoc} */
	public <D extends Sketch> D resolve(final SketchKey key, final Class<D> clazz) {
		Assertion.check()
				.isNotNull(key)
				.isNotNull(clazz)
				.isTrue(contains(key), "no sketch found with key {0} in {1}", key, sketches.keySet());
		//---
		return clazz.cast(sketches.get(key));
	}

	/** {@inheritDoc} */
	public Set<Class<? extends Sketch>> getAllTypes() {
		return sketches.values()
				.stream()
				.map(Sketch::getClass)
				.collect(Collectors.toSet());
	}

	/** {@inheritDoc} */
	public <S extends Sketch> List<S> getAll(final Class<S> clazz) {
		Assertion.check().isNotNull(clazz);
		//---
		return sketches.values()
				.stream()
				.filter(model -> clazz.isAssignableFrom(model.getClass()))
				.map(clazz::cast)
				.sorted(Comparator.comparing(s -> s.getKey().getName()))
				.collect(Collectors.toList());
	}
}

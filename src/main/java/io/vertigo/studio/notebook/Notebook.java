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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;

/**
 * A Studio notebook is a space where models can be shared and modified. (not threadSafe).
 * It is a conceptual representation of a project design that is human readable, storable and comparable
 *
 * @author mlaroche, pchretien
 */
public final class Notebook {
	private final Map<String, Sketch> sketchs = new LinkedHashMap<>();

	/**
	 * Register a new Definition.
	 * The definition must not be already registered.
	 * @param sketch the definition
	 */
	public void registerModel(final Sketch sketch) {
		Assertion.check().isNotNull(sketch);
		final String name = sketch.getName();
		Assertion.check().isFalse(sketchs.containsKey(name), "this model '{0}' is already registered", name);
		//-----
		sketchs.put(name, sketch);
	}

	/** {@inheritDoc} */
	public boolean contains(final String name) {
		Assertion.check().isNotNull(name);
		//-----
		return sketchs.containsKey(name);
	}

	/** {@inheritDoc} */
	public <D extends Sketch> D resolve(final String name, final Class<D> clazz) {
		Assertion.check()
				.isNotNull(name)
				.isNotNull(clazz);
		//-----
		final Sketch sketch = sketchs.get(name);
		Assertion.check().isNotNull(sketch, "Model '{0}' of type '{1}' not found in ({2})", name, clazz.getSimpleName(), sketchs.keySet());
		return clazz.cast(sketch);
	}

	/** {@inheritDoc} */
	public Set<Class<? extends Sketch>> getAllTypes() {
		return sketchs.values()
				.stream()
				.map(Sketch::getClass)
				.collect(Collectors.toSet());
	}

	/** {@inheritDoc} */
	public <C extends Sketch> Set<C> getAll(final Class<C> clazz) {
		Assertion.check().isNotNull(clazz); // Le type des objets recherchés ne peut pas être null
		//-----
		return sketchs.values()
				.stream()
				.filter(model -> clazz.isAssignableFrom(model.getClass()))
				.map(clazz::cast)
				.sorted(Comparator.comparing(Sketch::getName))
				.collect(Collectors.toSet());
	}

	/**
	 * Clear all known models
	 */
	public void clear() {
		sketchs.clear();
	}
}

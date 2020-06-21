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
package io.vertigo.studio.metamodel;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.DefinitionUtil;

/**
 * Definitions Repository. (not threadSafe).
 *
 * @author pchretien, mlaroche
 */
public final class MetamodelRepository implements DefinitionSpace {
	private final Map<String, Definition> definitions = new LinkedHashMap<>();

	/**
	 * Register a new Definition.
	 * The definition must not be already registered.
	 * @param definition the definition
	 */
	public void registerDefinition(final Definition definition) {
		Assertion.check().notNull(definition);
		final String name = definition.getName();
		DefinitionUtil.checkName(name, definition.getClass());
		Assertion.check().argument(!definitions.containsKey(name), "this definition '{0}' is already registered", name);
		//-----
		definitions.put(name, definition);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(final String name) {
		Assertion.check().notNull(name);
		//-----
		return definitions.containsKey(name);
	}

	/** {@inheritDoc} */
	@Override
	public <D extends Definition> D resolve(final String name, final Class<D> clazz) {
		Assertion.check()
				.notNull(name)
				.notNull(clazz);
		//-----
		final Definition definition = definitions.get(name);
		Assertion.check().notNull(definition, "Definition '{0}' of type '{1}' not found in ({2})", name, clazz.getSimpleName(), definitions.keySet());
		return clazz.cast(definition);
	}

	/** {@inheritDoc} */
	@Override
	public Set<Class<? extends Definition>> getAllTypes() {
		return definitions.values()
				.stream()
				.map(Definition::getClass)
				.collect(Collectors.toSet());
	}

	/** {@inheritDoc} */
	@Override
	public <C extends Definition> Set<C> getAll(final Class<C> clazz) {
		Assertion.check().notNull(clazz); // Le type des objets recherchés ne peut pas être null
		//-----
		return definitions.values()
				.stream()
				.filter(definition -> clazz.isAssignableFrom(definition.getClass()))
				.map(clazz::cast)
				.sorted(Comparator.comparing(Definition::getName))
				.collect(Collectors.toSet());
	}

	/**
	 * Clear all known definitions
	 */
	public void clear() {
		definitions.clear();
	}
}

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

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.DefinitionUtil;

/**
 * Espace de définitions (non threadSafe).
 *
 * @author pchretien, mlaroche
 */
public final class MetamodelRepository implements DefinitionSpace {
	private final Map<String, Definition> definitions = new LinkedHashMap<>();

	/**
	 * Enregistrement d'un nouvel object.
	 * @param definition Objet à enregistrer
	 */
	public void registerDefinition(final Definition definition) {
		Assertion.check().notNull(definition, "A definition can't be null.");
		final String name = definition.getName();
		DefinitionUtil.checkName(name, definition.getClass());
		Assertion.checkArgument(!definitions.containsKey(name), "this definition '{0}' is already registered", name);
		//-----
		definitions.put(name, definition);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(final String name) {
		return definitions.containsKey(name);
	}

	/** {@inheritDoc} */
	@Override
	public <D extends Definition> D resolve(final String name, final Class<D> clazz) {
		Assertion.checkNotNull(name);
		Assertion.checkNotNull(clazz);
		//-----
		final Definition definition = definitions.get(name);
		Assertion.checkNotNull(definition, "Definition '{0}' of type '{1}' not found in ({2})", name, clazz.getSimpleName(), definitions.keySet());
		return clazz.cast(definition);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Class<? extends Definition>> getAllTypes() {
		return definitions.values()
				.stream()
				.map(Definition::getClass)
				.collect(Collectors.toSet());
	}

	/** {@inheritDoc} */
	@Override
	public <C extends Definition> Collection<C> getAll(final Class<C> clazz) {
		Assertion.checkNotNull(clazz); // Le type des objets recherchés ne peut pas être null
		//-----
		return definitions.values()
				.stream()
				.filter(definition -> clazz.isAssignableFrom(definition.getClass()))
				.map(clazz::cast)
				.sorted(Comparator.comparing(Definition::getName))
				.collect(Collectors.toList());
	}

	/**
	 * Clear all known definitions
	 */
	public void clear() {
		definitions.clear();
	}

}

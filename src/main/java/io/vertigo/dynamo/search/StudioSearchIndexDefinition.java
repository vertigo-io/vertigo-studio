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
package io.vertigo.dynamo.search;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionPrefix;
import io.vertigo.datamodel.structure.metamodel.DtStereotype;
import io.vertigo.dynamo.domain.metamodel.StudioDtDefinition;
import io.vertigo.dynamo.domain.metamodel.StudioDtField;

/**
 * Définition de l'index de recherche.
 *
 * Fondalementalement un index est constitué de deux types d'objets.
 * - Un objet d'index (les champs indexés)
 * - Un keyConcept représentant le concept métier réprésenté par cet index.
 * La définition d'index précise également un SearchLoader permettant la mise à jour autonome de l'index.
 *
 * L'objet d'index est à la fois porteur des champs de recherche, et ceux utilisé à l'affichage.
 * La différence entre les deux peut-être affiné par :
 * - la propriété 'persistent' des fields pour savoir si le champs fait partit ou non du résultat utilisé pour l'affichage
 * - le domain et sa propriété indexType pour savoir si le champs est indéxé ou non
 *
 * L'objet d'affichage peut être simple (Ex: résultat google) alors qu'il se réfère à un index plus riche.
 *
 * @author dchallas, npiedeloup
 */
@DefinitionPrefix("StIdx")
public final class StudioSearchIndexDefinition implements Definition {

	/** Nom de l'index. */
	private final String name;

	/** Structure des éléments indexés. */
	private final StudioDtDefinition indexDtDefinition;

	private final StudioDtDefinition keyConceptDtDefinition;

	private final Map<StudioDtField, List<StudioDtField>> indexCopyToFieldsMap;

	private final String searchLoaderId;

	/**
	 * Constructor.
	 * @param name Index name
	 * @param keyConceptDtDefinition KeyConcept associé à l'index
	 * @param indexDtDefinition Structure des éléments indexés.
	 * @param indexCopyToFieldsMap CopyField map : (map fromField : [toField, toField, ...])
	 * @param searchLoaderId Loader de chargement des éléments indéxés et résultat
	 */
	public StudioSearchIndexDefinition(
			final String name,
			final StudioDtDefinition keyConceptDtDefinition,
			final StudioDtDefinition indexDtDefinition,
			final Map<StudioDtField, List<StudioDtField>> indexCopyToFieldsMap,
			final String searchLoaderId) {
		Assertion.checkArgNotEmpty(name);
		Assertion.checkNotNull(keyConceptDtDefinition);
		Assertion.checkArgument(
				keyConceptDtDefinition.getStereotype() == DtStereotype.KeyConcept,
				"keyConceptDtDefinition ({0}) must be a DtDefinition of a KeyConcept class", keyConceptDtDefinition.getName());
		Assertion.checkNotNull(indexDtDefinition);
		Assertion.checkNotNull(indexCopyToFieldsMap);
		Assertion.checkArgNotEmpty(searchLoaderId);
		//-----
		this.name = name;
		this.keyConceptDtDefinition = keyConceptDtDefinition;
		this.indexDtDefinition = indexDtDefinition;
		this.indexCopyToFieldsMap = indexCopyToFieldsMap;
		this.searchLoaderId = searchLoaderId;

	}

	/**
	 * Définition de l'objet représentant le contenu de l'index (indexé et résultat).
	 * @return Définition des champs indexés.
	 */
	public StudioDtDefinition getIndexDtDefinition() {
		return indexDtDefinition;
	}

	/**
	 * Définition du keyConcept maitre de cet index.
	 * Le keyConcept de l'index est surveillé pour rafraichir l'index.
	 * @return Définition du keyConcept.
	 */
	public StudioDtDefinition getKeyConceptDtDefinition() {
		return keyConceptDtDefinition;
	}

	/**
	 * @param toField Field to copy from others
	 * @return list des copyToFields.
	 */
	public List<StudioDtField> getIndexCopyToFromFields(final StudioDtField toField) {
		final List<StudioDtField> copyToFields = indexCopyToFieldsMap.get(toField);
		Assertion.checkNotNull(copyToFields);
		//-----
		return Collections.unmodifiableList(copyToFields);
	}

	/**
	 * @return copyFields from.
	 */
	public Set<StudioDtField> getIndexCopyToFields() {
		return Collections.unmodifiableSet(indexCopyToFieldsMap.keySet());
	}

	/**
	 * Nom du composant de chargement des éléments à indexer.
	 * @return Nom du composant de chargement des éléments à indexer.
	 */
	public String getSearchLoaderId() {
		return searchLoaderId;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name;
	}
}

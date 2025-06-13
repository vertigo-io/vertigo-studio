/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.notebook.search;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.StudioStereotype;

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
@SkecthPrefix(SearchIndexSketch.PREFIX)
public final class SearchIndexSketch extends AbstractSketch {
	public static final String PREFIX = "Idx";

	/** Structure des éléments indexés. */
	private final DtSketch indexDtSketch;

	private final DtSketch keyConceptDtSketch;

	private final Map<DtSketchField, List<DtSketchField>> indexCopyToFieldsMap;

	private final String searchLoaderId;

	/**
	 * Constructor.
	 * @param name Index name
	 * @param keyConceptDtSketch KeyConcept associé à l'index
	 * @param indexDtSketch Structure des éléments indexés.
	 * @param indexCopyToFieldsMap CopyField map : (map fromField : [toField, toField, ...])
	 * @param searchLoaderId Loader de chargement des éléments indéxés et résultat
	 */
	public SearchIndexSketch(
			final String name,
			final DtSketch keyConceptDtSketch,
			final DtSketch indexDtSketch,
			final Map<DtSketchField, List<DtSketchField>> indexCopyToFieldsMap,
			final String searchLoaderId) {
		super(name);
		//---
		Assertion.check()
				.isNotNull(keyConceptDtSketch)
				.isTrue(
						keyConceptDtSketch.getStereotype() == StudioStereotype.KeyConcept,
						"keyConceptDtSketch ({0}) must be a DtSketch of a KeyConcept class", keyConceptDtSketch)
				.isNotNull(indexDtSketch)
				.isNotNull(indexCopyToFieldsMap)
				.isNotBlank(searchLoaderId);
		//-----
		this.keyConceptDtSketch = keyConceptDtSketch;
		this.indexDtSketch = indexDtSketch;
		this.indexCopyToFieldsMap = indexCopyToFieldsMap;
		this.searchLoaderId = searchLoaderId;
	}

	/**
	 * Définition de l'objet représentant le contenu de l'index (indexé et résultat).
	 * @return Définition des champs indexés.
	 */
	public DtSketch getIndexDtSketch() {
		return indexDtSketch;
	}

	/**
	 * Définition du keyConcept maitre de cet index.
	 * Le keyConcept de l'index est surveillé pour rafraichir l'index.
	 * @return Définition du keyConcept.
	 */
	public DtSketch getKeyConceptDtSketch() {
		return keyConceptDtSketch;
	}

	/**
	 * @param toField Field to copy from others
	 * @return list des copyToFields.
	 */
	public List<DtSketchField> getIndexCopyToFromFields(final DtSketchField toField) {
		final List<DtSketchField> copyToFields = indexCopyToFieldsMap.get(toField);
		Assertion.check().isNotNull(copyToFields);
		//-----
		return Collections.unmodifiableList(copyToFields);
	}

	/**
	 * @return copyFields from.
	 */
	public Set<DtSketchField> getIndexCopyToFields() {
		return Collections.unmodifiableSet(indexCopyToFieldsMap.keySet());
	}

	/**
	 * Nom du composant de chargement des éléments à indexer.
	 * @return Nom du composant de chargement des éléments à indexer.
	 */
	public String getSearchLoaderId() {
		return searchLoaderId;
	}
}

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

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.locale.MessageText;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionPrefix;
import io.vertigo.datafactory.collections.model.FacetValue;
import io.vertigo.dynamo.domain.metamodel.StudioDtDefinition;
import io.vertigo.dynamo.domain.metamodel.StudioDtField;

/**
 * Définition de Facette.
 * Une facette porte sur un champ donné de l'index.
 * On distingue deux types de facettes.
 * - celles remontant les terms d'un champ
 * - celles remontant les valeurs d'une facette discrétisée par une liste de segments.
 *
 * Une facette
 *  - est identifiés par un nom unique au sein de son index.
 *  - posséde un Titre.
 *
 * Exemple :
 * Pour une liste d'articles, on créera des définitions de facette
 *  - pour segmenter les prix,
 *  	. 0-10€
 *  	. 10-50€
 *  	. >50€
 *  - pour donner les principaux fabricants, (facette de type 'term')
 *  - etc..
 *
 * @author pchretien
 */
@DefinitionPrefix("StFct")
public final class StudioFacetDefinition implements Definition {
	private final String name;
	private final StudioDtDefinition indexDtDefinition;
	private final StudioDtField dtField;
	private final MessageText label;
	private final List<FacetValue> facetValues;
	private final boolean rangeFacet;
	private final boolean multiSelectable;
	private final FacetOrder order;

	/**
	 * Facet order : alpha, count, definition
	 */
	public enum FacetOrder {
		/** alphabetical */
		alpha,
		/** count (default for term) */
		count,
		/** definition order (default for range) */
		definition
	}

	/**
	 * Constructor.
	 * @param name the name of the facet
	 * @param indexDtDefinition the dtDefinition of the facet
	 * @param dtField the field of the facet
	 * @param facetValues the list of filters
	 * @param rangeFacet if the facet is of type 'range'
	 * @param multiSelectable Can select multiple values
	 * @param order Facet Order
	 */
	private StudioFacetDefinition(
			final String name,
			final StudioDtDefinition indexDtDefinition,
			final StudioDtField dtField,
			final MessageText label,
			final List<FacetValue> facetValues,
			final boolean rangeFacet,
			final boolean multiSelectable,
			final FacetOrder order) {
		Assertion.checkArgNotEmpty(name);
		Assertion.checkNotNull(indexDtDefinition);
		Assertion.checkNotNull(dtField);
		Assertion.checkNotNull(label);
		Assertion.checkNotNull(facetValues);
		Assertion.when(rangeFacet)
				.check(() -> !facetValues.isEmpty(), "Les FacetDefinition de type 'range' doivent fournir la liste des segments non vides (FacetValues)");
		Assertion.when(!rangeFacet)
				.check(facetValues::isEmpty, "Les FacetDefinition de type 'term' doivent fournir une liste des segments vide");
		Assertion.checkNotNull(order);
		//-----
		this.name = name;
		this.indexDtDefinition = indexDtDefinition;
		this.dtField = dtField;
		this.label = label;
		this.facetValues = Collections.unmodifiableList(facetValues);
		this.rangeFacet = rangeFacet;
		this.multiSelectable = multiSelectable;
		this.order = order;
	}

	/**
	 * Creates a new facetDefinition of type 'range'.
	 *
	 * A range facet is defined by a list of filters.
	 * Examples :
	 * [0 -10[
	 * [0-100[
	 * [100-*[
	 * @param name the name of the facet
	 * @param indexDtDefinition the dtDefinition of the facet
	 * @param dtField the field of the facet
	 * @param label the label of the facet
	 * @param facetValues the list of filters
	 * @param multiSelectable Can select multiple values
	 * @param order Facet Order
	 * @return new facetDefinition of type 'range'
	 */
	public static StudioFacetDefinition createFacetDefinitionByRange(
			final String name,
			final StudioDtDefinition indexDtDefinition,
			final StudioDtField dtField,
			final MessageText label,
			final List<FacetValue> facetValues,
			final boolean multiSelectable,
			final FacetOrder order) {
		return new StudioFacetDefinition(name, indexDtDefinition, dtField, label, facetValues, true, multiSelectable, order);
	}

	/**
	 * Creates a new facetDefinition of type 'term'.
	 *
	 * @param name the name of the facet
	 * @param indexDtDefinition the dtDefinition of the facet
	 * @param dtField the field of the facet
	 * @param label the label of the facet
	 * @param multiSelectable Can select multiple values
	 * @param order Facet Order
	 * @return new facetDefinition of type 'term'
	 */
	public static StudioFacetDefinition createFacetDefinitionByTerm(
			final String name,
			final StudioDtDefinition indexDtDefinition,
			final StudioDtField dtField,
			final MessageText label,
			final boolean multiSelectable,
			final FacetOrder order) {
		return new StudioFacetDefinition(name, indexDtDefinition, dtField, label, Collections.emptyList(), false, multiSelectable, order);
	}

	/**
	 * @return the label of the facet
	 */
	public MessageText getLabel() {
		return label;
	}

	/**
	 * Le DtDefinition de l'index.
	 * @return DtDefinition sur lequel porte la facette
	 */
	public StudioDtDefinition getIndexDtDefinition() {
		return indexDtDefinition;
	}

	/**
	 * Ce champ est nécessairement inclus dans l'index.
	 * @return Champ sur lequel porte la facette
	 */
	public StudioDtField getDtField() {
		return dtField;
	}

	/**
	 * @return Liste des sélections/range.
	 */
	public List<FacetValue> getFacetRanges() {
		Assertion.checkArgument(rangeFacet, "Cette facette ({0}) n'est pas segmentée.", getName());
		//-----
		return facetValues;
	}

	/**
	 * @return if the facet is of type 'range'
	 */
	public boolean isRangeFacet() {
		return rangeFacet;
	}

	/**
	 * @return if the facet is multiSelectable
	 */
	public boolean isMultiSelectable() {
		return multiSelectable;
	}

	/**
	 * @return facet order
	 */
	public FacetOrder getOrder() {
		return order;
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

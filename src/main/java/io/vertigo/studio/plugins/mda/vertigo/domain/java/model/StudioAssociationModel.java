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
package io.vertigo.studio.plugins.mda.vertigo.domain.java.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketchNode;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;

/**
 * Objet utilisé par FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class StudioAssociationModel {
	private final AssociationSketch associationDefinition;
	private final AssociationSketchNode associationSketchNode;

	/**
	 * Constructeur.
	 * @param associationSketchNode Noeud de l'association à générer
	 */
	StudioAssociationModel(final AssociationSketch associationDefinition, final AssociationSketchNode associationSketchNode) {
		Assertion.check()
				.isNotNull(associationDefinition)
				.isNotNull(associationSketchNode);
		//-----
		this.associationDefinition = associationDefinition;
		this.associationSketchNode = associationSketchNode;
	}

	/**
	 * @return Definition d'une association.
	 */
	public AssociationSketch getDefinition() {
		return associationDefinition;
	}

	/**
	 * @return Label du noeud
	 */
	public String getLabel() {
		return associationSketchNode.getLabel();
	}

	/**
	 * @return Role du noeud
	 */
	public String getRole() {
		return associationSketchNode.getRole();
	}

	/**
	 * @return Si la cardinalité max du noeud est multiple
	 */
	public boolean isMultiple() {
		return associationSketchNode.isMultiple();
	}

	/**
	 * @return Type de l'association : Simple ou NN
	 */
	public boolean isSimple() {
		return getDefinition() instanceof AssociationSimpleSketch;
	}

	/**
	 * @return Si le noeud est navigable
	 */
	public boolean isNavigable() {
		return associationSketchNode.isNavigable();
	}

	/**
	 * @return Type à retourner
	 */
	public String getReturnType() {
		return associationSketchNode.getDtDefinition().getClassCanonicalName();
	}

	/**
	 * @return Type à retourner
	 */
	public String getReturnTypeSimpleName() {
		return associationSketchNode.getDtDefinition().getClassSimpleName();
	}

	/**
	 * @return Type à retourner
	 */
	public boolean isTargetStaticMasterData() {
		return associationSketchNode.getDtDefinition().getStereotype() == StudioStereotype.StaticMasterData;
	}

	/**
	 * @return Urn de la définition de l'association
	 */
	public String getUrn() {
		return associationDefinition.getName();
	}

	/**
	 * @return Nom du champ fk en camelCase.
	 */
	public String getFkFieldName() {
		return ((AssociationSimpleSketch) associationDefinition).getFKField().getName();
	}
}

/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2023, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.java.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketchNode;

/**
 * Objet utilisé par FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class StudioAssociationModel {
	private final AssociationSketch associationSketch;
	private final AssociationSketchNode associationSketchNode;

	/**
	 * Constructeur.
	 * @param associationSketchNode Noeud de l'association à générer
	 */
	StudioAssociationModel(final AssociationSketch associationSketch, final AssociationSketchNode associationSketchNode) {
		Assertion.check()
				.isNotNull(associationSketch)
				.isNotNull(associationSketchNode);
		//-----
		this.associationSketch = associationSketch;
		this.associationSketchNode = associationSketchNode;
	}

	/**
	 * @return Definition d'une association.
	 */
	public AssociationSketch getSketch() {
		return associationSketch;
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
		return getSketch() instanceof AssociationSimpleSketch;
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
		return associationSketchNode.getDtSketch().getClassCanonicalName();
	}

	/**
	 * @return Type à retourner
	 */
	public String getReturnTypeSimpleName() {
		return associationSketchNode.getDtSketch().getClassSimpleName();
	}

	/**
	 * @return Type à retourner
	 */
	public boolean isTargetStaticMasterData() {
		return associationSketchNode.getDtSketch().getStereotype() == StudioStereotype.StaticMasterData;
	}

	/**
	 * @return Urn de la définition de l'association
	 */
	public String getUrn() {
		return associationSketch.getKey().getName();
	}

	/**
	 * @return Nom du champ fk en camelCase.
	 */
	public String getFkFieldName() {
		return ((AssociationSimpleSketch) associationSketch).getFKField().getName();
	}
}

/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.ksp.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.notebook.domain.association.AssociationUtil;

/**
 * Objet utilisé par FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public class KspAssociationModel {
	private final AssociationSketch associationSketch;

	/**
	 * Constructeur.
	 * @param associationSketchNode Noeud de l'association à générer
	 */
	public KspAssociationModel(final AssociationSketch associationSketch) {
		Assertion.check()
				.isNotNull(associationSketch);
		//-----
		this.associationSketch = associationSketch;
	}

	public String getDtDefinitionA() {
		return "Dt" + associationSketch.getAssociationNodeA().getDtSketch().getLocalName();
	}

	public String getRoleA() {
		return associationSketch.getAssociationNodeA().getRole();
	}

	public String getLabelA() {
		return associationSketch.getAssociationNodeA().getLabel();
	}

	public String getMultiplicityA() {
		return AssociationUtil.getMultiplicity(
				associationSketch.getAssociationNodeA().isNotNull(),
				associationSketch.getAssociationNodeA().isMultiple());
	}

	public String getNavigabilityA() {
		return Boolean.toString(associationSketch.getAssociationNodeA().isNavigable());
	}

	public String getDtDefinitionB() {
		return "Dt" + associationSketch.getAssociationNodeB().getDtSketch().getLocalName();
	}

	public String getRoleB() {
		return associationSketch.getAssociationNodeB().getRole();
	}

	public String getLabelB() {
		return associationSketch.getAssociationNodeB().getLabel();
	}

	public String getMultiplicityB() {
		return AssociationUtil.getMultiplicity(
				associationSketch.getAssociationNodeB().isNotNull(),
				associationSketch.getAssociationNodeB().isMultiple());
	}

	public String getNavigabilityB() {
		return Boolean.toString(associationSketch.getAssociationNodeB().isNavigable());
	}

	protected AssociationSketch getAssociationSketch() {
		return associationSketch;
	}

}

/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.mermaid.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;

/**
 * Objet utilisé par FreeMarker.
 *
 * @author  mlaroche
 */
public final class MermaidNNAssociationModel {
	private final AssociationNNSketch associationNNSketch;

	/**
	 * Constructeur.
	 * @param associationSketchNode Noeud de l'association à générer
	 */
	MermaidNNAssociationModel(final AssociationNNSketch associationNNSketch) {
		Assertion.check()
				.isNotNull(associationNNSketch);
		//-----
		this.associationNNSketch = associationNNSketch;
	}

	/**
	 * @return Definition d'une association.
	 */
	public AssociationNNSketch getSketch() {
		return associationNNSketch;
	}

	/**
	 * @return Type à retourner
	 */
	public String getNodeADtSketchName() {
		return associationNNSketch.getAssociationNodeA().getDtSketch().getLocalName();
	}

	/**
	 * @return Type à retourner
	 */
	public String getNodeBDtSketchName() {
		return associationNNSketch.getAssociationNodeB().getDtSketch().getLocalName();
	}

}

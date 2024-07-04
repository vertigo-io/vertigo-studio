/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;

/**
 * Objet utilisé par FreeMarker.
 *
 * @author  mlaroche
 */
public final class MermaidSimpleAssociationModel {
	private final AssociationSimpleSketch associationSimpleSketch;

	/**
	 * Constructeur.
	 * @param associationSketchNode Noeud de l'association à générer
	 */
	MermaidSimpleAssociationModel(final AssociationSimpleSketch associationSketch) {
		Assertion.check()
				.isNotNull(associationSketch);
		//-----
		associationSimpleSketch = associationSketch;
	}

	/**
	 * @return Definition d'une association.
	 */
	public AssociationSketch getSketch() {
		return associationSimpleSketch;
	}

	/**
	 * @return Label du noeud
	 */
	public String getLabel() {
		return associationSimpleSketch.getPrimaryAssociationNode().getLabel();
	}

	/**
	 * @return Role du noeud
	 */
	public String getRole() {
		return associationSimpleSketch.getPrimaryAssociationNode().getRole();
	}

	/**
	 * @return Si la cardinalité max du noeud est multiple
	 */
	public boolean isMultiple() {
		return associationSimpleSketch.getPrimaryAssociationNode().isMultiple();
	}

	/**
	 * @return Si le noeud est navigable
	 */
	public boolean isNavigable() {
		return associationSimpleSketch.getPrimaryAssociationNode().isNavigable();
	}

	/**
	 * @return Type à retourner
	 */
	public String getPrimaryDtSketchName() {
		return associationSimpleSketch.getPrimaryAssociationNode().getDtSketch().getLocalName();
	}

	/**
	 * @return Type à retourner
	 */
	public String getPrimaryCardinality() {
		return cardinalityToMermaid(associationSimpleSketch.getFKField().getCardinality());
	}

	/**
	 * @return Type à retourner
	 */
	public String getForeignDtSketchName() {
		return associationSimpleSketch.getForeignAssociationNode().getDtSketch().getLocalName();
	}

	/**
	 * @return Type à retourner
	 */
	public String getForeignCardinality() {
		if (associationSimpleSketch.getForeignAssociationNode().isMultiple()) {
			return cardinalityToMermaid(Cardinality.MANY);
		}
		// one or nullable
		if (associationSimpleSketch.getForeignAssociationNode().isNotNull()) {
			return cardinalityToMermaid(Cardinality.ONE);
		}
		return cardinalityToMermaid(Cardinality.OPTIONAL_OR_NULLABLE);
	}

	/**
	 * @return Urn de la définition de l'association
	 */
	public String getUrn() {
		return associationSimpleSketch.getKey().getName();
	}

	/**
	 * @return Nom du champ fk en camelCase.
	 */
	public String getFkFieldName() {
		return associationSimpleSketch.getFKField().getName();
	}

	private static String cardinalityToMermaid(final Cardinality cardinality) {
		return switch (cardinality) {
			case ONE -> "1";
			case OPTIONAL_OR_NULLABLE -> "0..1";
			case MANY -> "*";
			default -> throw new VSystemException("Unsuppported cardinality '{0}'", cardinality);
		};
	}
}

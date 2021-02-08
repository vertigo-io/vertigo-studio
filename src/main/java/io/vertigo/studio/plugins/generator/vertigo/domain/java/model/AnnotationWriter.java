/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.vertigo.core.lang.Cardinality;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketchNode;
import io.vertigo.studio.notebook.domain.association.AssociationUtil;
import io.vertigo.studio.plugins.generator.vertigo.VertigoConstants.VertigoClassNames;
import io.vertigo.studio.tools.SketchUtil;

/**
 * Gestion centralisée des annotations sur les objets générés.
 *
 * @author pchretien, mlaroche
 */
class AnnotationWriter {

	/** Chaine d'indentation. */
	private static final String INDENT = "\t\t";

	/**
	 * Ecriture des annotations sur une propriété méta.
	 *
	 * @param propertyName Property Name
	 * @return Liste des lignes de code java à ajouter.
	 */
	List<String> writeAnnotations(final String propertyName) {
		if ("UID".equalsIgnoreCase(propertyName)) {
			return writeUIDAnnotations();
		}
		throw new UnsupportedOperationException("This property (" + propertyName + ") is not supported on domain MDA");
	}

	/**
	 * Ecriture des annotations transient.
	 *
	 * @return Liste des lignes de code java à ajouter.
	 */
	List<String> writeTransientAnnotations() {
		// basic is nothing
		return Collections.emptyList();
	}

	/**
	 * Ectiture des annotations sur une DT_DEFINITION.
	 *
	 * @param dtSketch DtDefinition
	 * @return Liste des lignes de code java à ajouter.
	 */
	List<String> writeAnnotations(final DtSketch dtSketch) {
		final List<String> lines = new ArrayList<>();
		if (dtSketch.getFragment().isPresent()) {
			// Générations des annotations Dynamo
			final StringBuilder buffer = new StringBuilder()
					.append('@').append(VertigoClassNames.Fragment.getClassName());
			if (dtSketch.getFragment().isPresent()) {
				buffer.append('(')
						.append("fragmentOf = \"").append(dtSketch.getFragment().get().getKey().getName()).append('\"')
						.append(')');
			}

			lines.add(buffer.toString());
		}
		if (dtSketch.isPersistent() && !"main".equals(dtSketch.getDataSpace())) {
			final String dataSpace = new StringBuilder()
					.append('@').append(VertigoClassNames.AnnotationDataSpace.getClassName())
					.append("(\"").append(dtSketch.getDataSpace()).append("\")")
					.toString();
			lines.add(dataSpace);
		}
		return lines;
	}

	/**
	 * Ectiture des annotations sur un DT_FIELD.
	 *
	 * @param dtField Champ de la DT_DEFINITION
	 * @return Liste des lignes de code java à ajouter.
	 */
	List<String> writeAnnotations(final DtSketchField dtField) {
		// Générations des annotations Dynamo
		// if we are a foreign key
		if (dtField.getType() == DtSketchField.FieldType.FOREIGN_KEY) {
			final StringBuilder buffer = new StringBuilder("@").append(VertigoClassNames.AnnotationForeignKey.getClassName()).append("(")
					.append("smartType = \"").append(dtField.getDomain().getSmartTypeName()).append("\", ")
					.append("label = \"").append(dtField.getLabel().getDisplay()).append("\", ")
					.append("fkDefinition = \"").append("Dt").append(SketchUtil.getLocalName(dtField.getFkDtSketchName(), DtSketch.PREFIX)).append("\"");
			// The cardinality is always here but OptionalOrNullable is the default in annotation so we skip it to limit verbosity
			if (Cardinality.OPTIONAL_OR_NULLABLE != dtField.getCardinality()) {
				buffer.append(", cardinality = ")
						.append(Cardinality.class.getCanonicalName())
						.append('.')
						.append(dtField.getCardinality().name());
			}
			buffer.append(" )");
			return Collections.singletonList(buffer.toString());
		}

		final List<String> lines = new ArrayList<>();
		// we are other type of field
		final StringBuilder buffer = new StringBuilder("@Field(")
				.append("smartType = \"").append(dtField.getDomain().getSmartTypeName()).append("\", ");
		if (dtField.getType() != DtSketchField.FieldType.DATA) {
			// "DATA" est la valeur par défaut de type dans l'annotation Field
			buffer.append("type = \"").append(dtField.getType()).append("\", ");
		}
		// The cardinality is always here but OptionalOrNullable is the default in annotation so we skip it to limit verbosity
		if (Cardinality.OPTIONAL_OR_NULLABLE != dtField.getCardinality()) {
			buffer.append("cardinality = ")
					.append(Cardinality.class.getCanonicalName())
					.append('.')
					.append(dtField.getCardinality().name())
					.append(", ");
		}
		if (!dtField.isPersistent()) {
			// On ne précise la persistance que si elle n'est pas gérée
			buffer.append("persistent = false, ");
		}
		buffer.append("label = \"")
				.append(dtField.getLabel().getDisplay())
				.append('\"')
				// on place le label a la fin, car il ne faut pas de ','
				.append(')');
		lines.add(buffer.toString());

		return lines;
	}

	/**
	 * Ectiture des annotations sur le getURI.
	 * @return Liste des lignes de code java à ajouter.
	 */
	List<String> writeUIDAnnotations() {
		return Collections.emptyList();
	}

	/**
	 * Ectiture des annotations sur un DT_FIELD gérant une association.
	 *
	 * @param associationSimple Definition de l'association
	 * @return Liste des lignes de code java à ajouter.
	 */
	List<String> writeSimpleAssociationAnnotation(final AssociationSimpleSketch associationSimple) {
		final AssociationSketchNode primaryNode = associationSimple.getPrimaryAssociationNode();
		final AssociationSketchNode foreignNode = associationSimple.getForeignAssociationNode();
		final String primaryMultiplicity = AssociationUtil.getMultiplicity(primaryNode.isNotNull(), primaryNode.isMultiple());
		final String foreignMultiplipicity = AssociationUtil.getMultiplicity(foreignNode.isNotNull(), foreignNode.isMultiple());

		return List.of(
				"@" + VertigoClassNames.AnnotationAssociation.getClassName() + "(",
				INDENT + "name = \"" + "A" + associationSimple.getLocalName() + "\",",
				INDENT + "fkFieldName = \"" + associationSimple.getFKField().getName() + "\",",
				INDENT + "primaryDtDefinitionName = \"" + "Dt" + primaryNode.getDtSketch().getLocalName() + "\",",
				INDENT + "primaryIsNavigable = " + primaryNode.isNavigable() + ',',
				INDENT + "primaryRole = \"" + primaryNode.getRole() + "\",",
				INDENT + "primaryLabel = \"" + primaryNode.getLabel() + "\",",
				INDENT + "primaryMultiplicity = \"" + primaryMultiplicity + "\",",
				INDENT + "foreignDtDefinitionName = \"" + "Dt" + foreignNode.getDtSketch().getLocalName() + "\",",
				INDENT + "foreignIsNavigable = " + foreignNode.isNavigable() + ',',
				INDENT + "foreignRole = \"" + foreignNode.getRole() + "\",",
				INDENT + "foreignLabel = \"" + foreignNode.getLabel() + "\",",
				INDENT + "foreignMultiplicity = \"" + foreignMultiplipicity + "\")");
	}

	/**
	 * Ectiture des annotations sur un DT_FIELD gérant une association.
	 *
	 * @param associationNN Definition de l'association
	 * @return Liste des lignes de code java à ajouter.
	 */
	List<String> writeNNAssociationAnnotation(final AssociationNNSketch associationNN) {
		final AssociationSketchNode nodeA = associationNN.getAssociationNodeA();
		final AssociationSketchNode nodeB = associationNN.getAssociationNodeB();

		return List.of(
				"@" + VertigoClassNames.AnnotationAssociationNN.getClassName() + "(",
				INDENT + "name = \"" + "Ann" + associationNN.getLocalName() + "\",",
				INDENT + "tableName = \"" + associationNN.getTableName() + "\",",
				INDENT + "dtDefinitionA = \"" + "Dt" + nodeA.getDtSketch().getLocalName() + "\",",
				INDENT + "dtDefinitionB = \"" + "Dt" + nodeB.getDtSketch().getLocalName() + "\",",
				INDENT + "navigabilityA = " + nodeA.isNavigable() + ',',
				INDENT + "navigabilityB = " + nodeB.isNavigable() + ',',
				INDENT + "roleA = \"" + nodeA.getRole() + "\",",
				INDENT + "roleB = \"" + nodeB.getRole() + "\",",
				INDENT + "labelA = \"" + nodeA.getLabel() + "\",",
				INDENT + "labelB = \"" + nodeB.getLabel() + "\")");
	}
}

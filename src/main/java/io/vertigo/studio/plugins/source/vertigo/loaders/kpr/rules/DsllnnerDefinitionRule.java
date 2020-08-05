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
package io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules;

import java.util.List;

import io.vertigo.commons.peg.AbstractRule;
import io.vertigo.commons.peg.PegRule;
import io.vertigo.commons.peg.PegRules;
import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketch;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketchBuilder;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.definition.DslSketchBody;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.definition.DslSketchEntry;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.definition.DslPropertyEntry;

final class DslInnerDefinitionRule extends AbstractRule<DslSketchEntry, List<Object>> {
	private final String entityName;
	private final DslEntity entity;

	DslInnerDefinitionRule(final String entityName, final DslEntity entity) {
		super(createMainRule(entityName, entity), entityName);
		this.entityName = entityName;
		this.entity = entity;
	}

	private static PegRule<List<Object>> createMainRule(final String entityName, final DslEntity entity) {
		Assertion.check()
				.isNotBlank(entityName)
				.isNotNull(entity);
		//-----
		final DslSketchBodyRule definitionBodyRule = new DslSketchBodyRule(entity);
		return PegRules.sequence(//"InnerDefinition"
				PegRules.term(entityName),
				DslSyntaxRules.SPACES,
				DslSyntaxRules.WORD, //2
				DslSyntaxRules.SPACES,
				definitionBodyRule, //4
				DslSyntaxRules.SPACES,
				PegRules.optional(DslSyntaxRules.OBJECT_SEPARATOR));
	}

	@Override
	protected DslSketchEntry handle(final List<Object> parsing) {
		//Dans le cas des sous définition :: field [PRD_XXX]

		final String definitionName = (String) parsing.get(2);
		final DslSketchBody definitionBody = (DslSketchBody) parsing.get(4);

		final DslSketchBuilder dslSketchBuilder = DslSketch.builder(definitionName, entity);
		populateDefinition(definitionBody, dslSketchBuilder);

		//---
		return new DslSketchEntry(entityName, dslSketchBuilder.build());
	}

	/**
	 * Peuple la définition à partir des éléments trouvés.
	 */
	private static void populateDefinition(final DslSketchBody definitionBody, final DslSketchBuilder dslDefinitionBuilder) {
		for (final DslSketchEntry fieldDefinitionEntry : definitionBody.getDefinitionEntries()) {
			//-----
			// 1.On vérifie que le champ existe pour la metaDefinition
			// et qu'elle n'est pas déjà enregistrée sur l'objet.
			//-----
			if (fieldDefinitionEntry.containsSketch()) {
				// On ajoute la définition par sa valeur.
				dslDefinitionBuilder.addChildDefinition(fieldDefinitionEntry.getFieldName(), fieldDefinitionEntry.getSketch());
			} else {
				// On ajoute les définitions par leur clé.
				dslDefinitionBuilder.addAllDefinitionLinks(fieldDefinitionEntry.getFieldName(), fieldDefinitionEntry.getDefinitionNames());
			}
		}
		for (final DslPropertyEntry dslPropertyEntry : definitionBody.getPropertyEntries()) {
			//			// On vérifie que la propriété est enregistrée sur la metaDefinition
			//			Assertion.precondition(definition.getEntity().getPropertySet().contains(fieldPropertyEntry.getProperty()), "Propriété {0} non enregistré sur {1}",
			//					fieldPropertyEntry.getProperty(), definition.getEntity().getName());
			//-----
			final Object value = readProperty(dslDefinitionBuilder.getEntity(), dslPropertyEntry);
			dslDefinitionBuilder.addPropertyValue(dslPropertyEntry.getPropertyName(), value);
		}
	}

	/**
	 * Retourne la valeur typée en fonction de son expression sous forme de String
	 * L'expression est celle utilisée dans le fichier xml/ksp.
	 * Cette méthode n'a pas besoin d'être optimisée elle est appelée au démarrage uniquement.
	 * @return J Valeur typée de la propriété
	 */
	private static Object readProperty(final DslEntity entity, final DslPropertyEntry dslPropertyEntry) {
		Assertion.check()
				.isNotNull(entity)
				.isNotNull(dslPropertyEntry);
		//-----
		return entity.getPropertyType(dslPropertyEntry.getPropertyName()).cast(dslPropertyEntry.getPropertyValueAsString());
	}

}

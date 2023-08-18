/*
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
package io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules;

import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.OBJECT_END;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.OBJECT_START;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.SPACES;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.commons.peg.AbstractRule;
import io.vertigo.commons.peg.PegChoice;
import io.vertigo.commons.peg.PegRule;
import io.vertigo.commons.peg.PegRules;
import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslEntityField;
import io.vertigo.studio.impl.source.dsl.entity.DslEntityLink;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw.DslPropertyEntry;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw.DslRawBody;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw.DslRawEntry;

/**
 * Règle définissant le corps d'une définition dynamo.
 * Une définition est composée d'une liste de
 * - couple (propriété, valeur)
 * - couple (champ, définition(s)).
 * Une définition étant soit affectée en ligne soit référencée.
 *
 * @author pchretien, mlaroche
 */
public final class DslRawBodyRule extends AbstractRule<DslRawBody, List<Object>> {

	/**
	 * Constructor.
	 */
	public DslRawBodyRule(final DslEntity entity) {
		super(createMainRule(entity), entity.getName() + "Body");
	}

	private static PegRule<List<Object>> createMainRule(final DslEntity entity) {
		Assertion.check().isNotNull(entity);
		final List<String> attributeNames = new ArrayList<>();

		final List<PegRule<?>> innerDefinitionRules = new ArrayList<>();

		for (final DslEntityField dslEntityField : entity.getFields()) {
			attributeNames.add(dslEntityField.getName());

			final DslEntity dslEntity;
			if (dslEntityField.getType() instanceof DslEntity) {
				dslEntity = DslEntity.class.cast(dslEntityField.getType());
			} else if (dslEntityField.getType() instanceof DslEntityLink) {
				dslEntity = DslEntityLink.class.cast(dslEntityField.getType()).getEntity();
			} else {
				//case property
				dslEntity = null;
			}
			if (dslEntity != null) {
				innerDefinitionRules.add(new DslInnerRawRule(dslEntityField.getName(), dslEntity));
			}
		}

		final DslPropertyDeclarationRule propertyDeclarationRule = new DslPropertyDeclarationRule(entity.getPropertyNames());
		final DslRawEntryRule xDefinitionEntryRule = new DslRawEntryRule(attributeNames);
		final PegRule<PegChoice> firstOfRule = PegRules.choice(
				propertyDeclarationRule, // 0
				xDefinitionEntryRule, // 1
				PegRules.choice(innerDefinitionRules), //2,
				SPACES);

		final PegRule<List<PegChoice>> manyRule = PegRules.zeroOrMore(firstOfRule, false);
		return PegRules.sequence(
				OBJECT_START,
				SPACES,
				manyRule, //2
				SPACES,
				OBJECT_END);
	}

	@Override
	protected DslRawBody handle(final List<Object> parsing) {
		final List<PegChoice> many = (List<PegChoice>) parsing.get(2);

		final List<DslRawEntry> fieldDefinitionEntries = new ArrayList<>();
		final List<DslPropertyEntry> fieldPropertyEntries = new ArrayList<>();
		for (final PegChoice item : many) {
			switch (item.choiceIndex()) {
				case 0:
					//Soit on est en présence d'une propriété standard
					final DslPropertyEntry propertyEntry = (DslPropertyEntry) item.value();
					fieldPropertyEntries.add(propertyEntry);
					break;
				case 1:
					final DslRawEntry xDefinitionEntry = (DslRawEntry) item.value();
					fieldDefinitionEntries.add(xDefinitionEntry);
					break;
				case 2:
					final PegChoice subTuple = (PegChoice) item.value();
					fieldDefinitionEntries.add((DslRawEntry) subTuple.value());
					break;
				case 3:
					break;
				default:
					throw new IllegalArgumentException("Type of rule not supported");
			}
		}
		return new DslRawBody(fieldDefinitionEntries, fieldPropertyEntries);
	}
}

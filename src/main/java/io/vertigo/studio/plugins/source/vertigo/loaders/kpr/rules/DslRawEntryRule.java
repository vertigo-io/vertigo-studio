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
package io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules;

import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.PAIR_SEPARATOR;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.SPACES;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.WORD;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslWordsRule.WORDS;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.commons.peg.AbstractRule;
import io.vertigo.commons.peg.PegChoice;
import io.vertigo.commons.peg.PegRule;
import io.vertigo.commons.peg.PegRules;
import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.raw.DslRawKey;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw.DslRawEntry;

/**
 * Règle de déclaration d'une champ référenéant une listes de clés.
 * @author pchretien, mlaroche
 */
public final class DslRawEntryRule extends AbstractRule<DslRawEntry, List<Object>> {

	/**
	 * Constructor.
	 * @param fieldNames List of field names
	 */
	public DslRawEntryRule(final List<String> fieldNames) {
		super(createMainRule(fieldNames));
	}

	private static PegRule<List<Object>> createMainRule(final List<String> fieldNames) {
		Assertion.check().isNotNull(fieldNames);
		//-----
		final List<PegRule<?>> fieldNamesRules = fieldNames
				.stream()
				.map(PegRules::term)
				.collect(Collectors.toList());
		//-----
		return PegRules.sequence(//"DefinitionKey"
				PegRules.choice(fieldNamesRules), //0
				SPACES,
				PAIR_SEPARATOR,
				SPACES,
				PegRules.choice(WORD, WORDS), //4
				SPACES,
				PegRules.optional(DslSyntaxRules.OBJECT_SEPARATOR));
	}

	@Override
	protected DslRawEntry handle(final List<Object> parsing) {
		final String fieldName = (String) ((PegChoice) parsing.get(0)).value();
		final List<DslRawKey> keys;

		final PegChoice definitionChoice = (PegChoice) parsing.get(4);
		keys = switch (definitionChoice.choiceIndex()) {
			case 1 -> ((List<String>) definitionChoice.value())
					.stream()
					.map(DslRawKey::of)
					.collect(Collectors.toList());
			case 0 -> {
				//Déclaration d'une définition identifiée par sa clé
				final String value = (String) definitionChoice.value();
				yield java.util.Collections.singletonList(DslRawKey.of(value));
			}
			default -> throw new IllegalStateException();
		};
		return new DslRawEntry(fieldName, keys);
	}
}

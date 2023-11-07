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

import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.SPACES;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.commons.peg.AbstractRule;
import io.vertigo.commons.peg.PegChoice;
import io.vertigo.commons.peg.PegRule;
import io.vertigo.commons.peg.PegRules;
import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw.DslRawEntry;

/*
 * @author pchretien, mlaroche
 */
public final class DslRawRule extends AbstractRule<DslRaw, PegChoice> {

	/**
	 * Constructor.
	 * @param grammar the grammar
	 */
	public DslRawRule(final String operation, final DslGrammar grammar) {
		super(createMainRule(operation, grammar), operation + "Definitions");
	}

	private static PegRule<PegChoice> createMainRule(final String operation, final DslGrammar grammar) {
		Assertion.check()
				.isNotBlank(operation)
				.isNotNull(grammar);
		//-----
		final List<PegRule<?>> rules = grammar.getEntities()
				.stream()
				.map(entity -> new DslInnerRawRule(entity.getName(), entity))
				.map(innerRawRule -> createRule(operation, innerRawRule))
				.toList();
		return PegRules.choice(rules);
	}

	private static PegRule<List<Object>> createRule(final String operation, final DslInnerRawRule innerRawRule) {
		// Création de la règle de déclaration d'une nouvelle definition.
		return PegRules.sequence(//Definition
				PegRules.term(operation), // alter ou create
				SPACES,
				innerRawRule, //2
				SPACES);
	}

	@Override
	protected DslRaw handle(final PegChoice parsing) {
		final DslRawEntry rawEntry = (DslRawEntry) ((List) parsing.value()).get(2);
		return rawEntry.getRaw();
	}
}

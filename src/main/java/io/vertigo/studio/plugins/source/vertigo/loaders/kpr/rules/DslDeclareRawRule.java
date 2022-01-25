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

import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.SPACES;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.WORD;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.commons.peg.AbstractRule;
import io.vertigo.commons.peg.PegChoice;
import io.vertigo.commons.peg.PegRule;
import io.vertigo.commons.peg.PegRules;
import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;

/*
 * @author mlaroche
 */
public final class DslDeclareRawRule extends AbstractRule<String, PegChoice> {

	/**
	 * Constructor.
	 * @param grammar the grammar
	 */
	public DslDeclareRawRule(final DslGrammar grammar) {
		super(createDeclareRule(grammar), "DeclareDefinitions");
	}

	private static PegRule<PegChoice> createDeclareRule(final DslGrammar grammar) {
		Assertion.check()
				.isNotNull(grammar);
		//-----
		final List<PegRule<?>> rules = grammar.getEntities()
				.stream()
				.map(entity -> createRule(entity))
				.collect(Collectors.toList());
		return PegRules.choice(rules);
	}

	private static PegRule<List<Object>> createRule(final DslEntity entity) {
		// Création de la règle de déclaration d'une nouvelle definition.
		return PegRules.sequence(//Definition
				PegRules.term("declare"),
				SPACES,
				PegRules.term(entity.getName()), //2
				SPACES,
				WORD, //4
				SPACES,
				PegRules.optional(DslSyntaxRules.OBJECT_SEPARATOR));
	}

	@Override
	protected String handle(final PegChoice parsing) {
		return (String) ((List) parsing.getValue()).get(4); // return the name of the external entity
	}
}

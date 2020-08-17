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

import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.BACK_QUOTATION_MARK;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.PAIR_SEPARATOR;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.PROPERTY_VALUE;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.QUOTATION_MARK;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.RAW_PROPERTY_VALUE;
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.SPACES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertigo.commons.peg.AbstractRule;
import io.vertigo.commons.peg.PegChoice;
import io.vertigo.commons.peg.PegRule;
import io.vertigo.commons.peg.PegRules;
import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.definition.DslPropertyEntry;

/**
 * règle de déclaration d'une affectation de propriété.
 * Dans la mesure ou l'on récupère un couple propriété, valeur on apparente
 * cela à une Entry (voir api Map).
 *
 * La propriété doit exister.
 * Syntaxe : nomPropriété: "valeur";
 * Exemple : maxLength:"10";
 *
 * @author pchretien, mlaroche
 */
public final class DslPropertyDeclarationRule extends AbstractRule<DslPropertyEntry, List<Object>> {
	private final Map<String, String> entityProperties;

	/**
	 * <propertyName> : "<propertyvalue>";
	 */
	public DslPropertyDeclarationRule(final Set<String> entityPropertyNames) {
		super(createMainRule(entityPropertyNames));
		Assertion.check().isNotNull(entityPropertyNames);
		//-----
		entityProperties = new HashMap<>();
		for (final String entityPropertyName : entityPropertyNames) {
			final String propertyName = StringUtil.constToLowerCamelCase(entityPropertyName);
			entityProperties.put(propertyName, entityPropertyName);
		}
	}

	private static PegRule<List<Object>> createMainRule(final Set<String> entityPropertyNames) {
		final List<PegRule<?>> propertyNamesRules = new ArrayList<>();
		for (final String entityPropertyName : entityPropertyNames) {
			propertyNamesRules.add(PegRules.term(StringUtil.constToLowerCamelCase(entityPropertyName)));
		}

		return PegRules.sequence(
				PegRules.choice(propertyNamesRules),
				SPACES,
				PAIR_SEPARATOR,
				SPACES,
				PegRules.choice(// choice is 4
						PegRules.sequence(QUOTATION_MARK,
								PROPERTY_VALUE, // inner 1
								QUOTATION_MARK),
						PegRules.sequence(BACK_QUOTATION_MARK,
								RAW_PROPERTY_VALUE, // inner 1
								BACK_QUOTATION_MARK)),
				SPACES,
				PegRules.optional(DslSyntaxRules.OBJECT_SEPARATOR));
	}

	@Override
	protected DslPropertyEntry handle(final List<Object> parsing) {
		final String propertyName = (String) ((PegChoice) parsing.get(0)).getValue();
		final PegChoice propertyChoice = (PegChoice) parsing.get(4);
		final String propertyValue = (String) ((List<?>) propertyChoice.getValue()).get(1);
		return new DslPropertyEntry(entityProperties.get(propertyName), propertyValue);
	}
}

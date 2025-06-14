/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
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

import io.vertigo.commons.peg.PegChoice;
import io.vertigo.commons.peg.rule.PegAbstractRule;
import io.vertigo.commons.peg.rule.PegRule;
import io.vertigo.commons.peg.rule.PegRules;
import io.vertigo.commons.peg.rule.PegRule.Dummy;
import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslRawRepository;

/**
 * règle de composition d'un fichier KSP.
 * Permet de parser un texte au format KSP.
 * Un  fichier KSP ne contient que
 *  - des déclarations de définition,
 *  - des déclarations de droits.
 *
 * Tout fichier ksp commence par une entête ou est précisé le nom du pacakage.
 *
 * @author pchretien, mlaroche
 */
public final class DslKspRule extends PegAbstractRule<Dummy, List<Object>> {
	private final DslRawRepository rawRepository;

	/**
	 * Constructor.
	 * @param rawRepository Grammaire
	 */
	public DslKspRule(final DslRawRepository rawRepository) {
		super(createMainRule(rawRepository.getGrammar()), "Ksp");
		this.rawRepository = rawRepository;
	}

	private static PegRule<List<Object>> createMainRule(final DslGrammar grammar) {
		Assertion.check().isNotNull(grammar);
		//-----
		final PegRule<DslRaw> definitionRule = new DslRawRule("create", grammar);
		final PegRule<DslRaw> templateRule = new DslRawRule("alter", grammar);
		final PegRule<String> declareRule = new DslDeclareRawRule(grammar);

		final PegRule<PegChoice> declarationChoiceRule = PegRules.choice(//"definition or template")
				definitionRule, //0
				templateRule, //1
				declareRule //2
		);
		final PegRule<List<PegChoice>> declarationChoicesRule = PegRules.zeroOrMore(declarationChoiceRule, true);
		return PegRules.sequence(
				SPACES,
				new DslPackageDeclarationRule(), //1
				SPACES,
				declarationChoicesRule); //3
	}

	@Override
	protected Dummy handle(final List<Object> parsing) {
		final String packageName = (String) parsing.get(1);
		final List<PegChoice> declarationChoices = (List<PegChoice>) parsing.get(3);

		for (final PegChoice declarationChoice : declarationChoices) {
			//Tant qu'il y a du texte, il doit correspondre
			// - à des définitions qui appartiennent toutes au même package.
			// - à des gestion de droits.
			switch (declarationChoice.choiceIndex()) {
				case 0:
					//On positionne le Package
					final DslRaw oldDynamicDefinition = (DslRaw) declarationChoice.value();
					final DslRaw newDynamicDefinition = DslRaw.builder(oldDynamicDefinition.getKey(), oldDynamicDefinition.getEntity())
							.withPackageName(packageName)
							.merge(oldDynamicDefinition)
							.build();
					handleDefinitionRule(newDynamicDefinition);
					break;
				case 1:
					handleTemplateRule((DslRaw) declarationChoice.value());
					break;
				case 2:
					//declare
					// do nothing we just want to declare in ksp that some sketch are defined in other resources but can be referenced in ksp files with no error
					// this is particularely useful for the Xtext plugin, that only parses ksp files
					break;
				default:
					throw new IllegalArgumentException("case " + declarationChoice.choiceIndex() + " not implemented");
			}
		}
		return Dummy.INSTANCE;
	}

	private void handleTemplateRule(final DslRaw dslDefinition) {
		rawRepository.addPartialSketch(dslDefinition);
	}

	private void handleDefinitionRule(final DslRaw dslDefinition) {
		rawRepository.addRaw(dslDefinition);
	}
}

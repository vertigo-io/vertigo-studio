/**
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
import static io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSyntaxRules.WORD;

import java.util.List;

import io.vertigo.commons.peg.AbstractRule;
import io.vertigo.commons.peg.PegRule;
import io.vertigo.commons.peg.PegRules;

/**
 * règle de déclaration d'un package.
 *
 * règle spécifiant qu'un package doit commencer par :
 * package nomdupackage;.
 * @author pchretien, mlaroche
 */
public final class DslPackageDeclarationRule extends AbstractRule<String, List<Object>> {

	public DslPackageDeclarationRule() {
		super(createMainRule());
	}

	private static PegRule<List<Object>> createMainRule() {
		return PegRules.sequence(
				PegRules.term("package "), //après package il y a un blanc obligatoire
				SPACES,
				WORD); // Nom du package 2
	}

	@Override
	protected String handle(final List<Object> parsing) {
		return (String) parsing.get(2); //Indice de la règle packageNamerule
	}
}

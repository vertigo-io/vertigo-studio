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
package io.vertigo.studio.metamodel.authorization;

import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionPrefix;
import io.vertigo.core.util.StringUtil;

/**
 * Une authorization est un droit sur une fonction de l'application.
 * Ou sur une opération sur une entite.
 * Sous condition d'un ensemble de règles.
 *
 * @author prahmoune, npiedeloup
 */
@DefinitionPrefix("SecF")
public final class SecuredFeature implements Definition {
	public static final String PREFIX = "SecF";
	//soit authorization globale (sans règle)
	//soit authorization = une opération sur une entity
	private final Optional<String> comment;
	private final String code;
	private final String label;

	private final Optional<String> linkedResourceOpt;

	/**
	 * Constructor.
	 *
	 * @param code Code de l'authorization
	 * @param label Label
	 * @param comment Comment
	 */
	public SecuredFeature(
			final String code,
			final String label,
			final Optional<String> comment,
			final Optional<String> linkedResourceOpt) {
		Assertion.check()
				.argNotEmpty(code)
				.argNotEmpty(label)
				.notNull(comment)
				.notNull(linkedResourceOpt);
		//-----
		this.code = code;
		this.label = label;
		this.comment = comment;
		this.linkedResourceOpt = linkedResourceOpt;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return PREFIX + StringUtil.first2UpperCase(code);
	}

	public String getCode() {
		return code;
	}

	/**
	 * @return Label de la authorization
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return Comment de la authorization
	 */
	public Optional<String> getComment() {
		return comment;
	}

	public Optional<String> getLinkedResourceOpt() {
		return linkedResourceOpt;
	}

}

/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.notebook.authorization;

import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;

/**
 * Une authorization est un droit sur une fonction de l'application.
 * Ou sur une opération sur une entite.
 * Sous condition d'un ensemble de règles.
 *
 * @author prahmoune, npiedeloup
 */
@SkecthPrefix(SecuredFeatureSketch.PREFIX)
public final class SecuredFeatureSketch extends AbstractSketch {
	public static final String PREFIX = "SecF";
	//soit authorization globale (sans règle)
	//soit authorization = une opération sur une entity
	private final Optional<String> commentOpt;
	private final String code;
	private final String label;

	private final Optional<String> linkedResourceOpt;

	/**
	 * Constructor.
	 *
	 * @param code Code de l'authorization
	 * @param label Label
	 * @param commentOpt Comment
	 */
	public SecuredFeatureSketch(
			final String code,
			final String label,
			final Optional<String> commentOpt,
			final Optional<String> linkedResourceOpt) {
		super(PREFIX + StringUtil.first2UpperCase(code));
		//---
		Assertion.check()
				.isNotBlank(code)
				.isNotBlank(label)
				.isNotNull(commentOpt)
				.isNotNull(linkedResourceOpt);
		//-----
		this.code = code;
		this.label = label;
		this.commentOpt = commentOpt;
		this.linkedResourceOpt = linkedResourceOpt;
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
		return commentOpt;
	}

	public Optional<String> getLinkedResourceOpt() {
		return linkedResourceOpt;
	}

}

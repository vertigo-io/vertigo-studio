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
package io.vertigo.studio.metamodel.domain;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.AbstractDefinition;
import io.vertigo.core.node.definition.DefinitionPrefix;

/**
 * Par nature un formatter est une ressource partagée et non modifiable.
 *
 * @author pchretien, mlaroche
 */
@DefinitionPrefix(FormatterDefinition.PREFIX)
public final class FormatterDefinition extends AbstractDefinition {
	public static final String PREFIX = "Fmt";
	/**
	* Nom de la contrainte.
	* On n'utilise pas les génériques car problémes.
	*/
	private final String formatterClassName;
	private final String args;

	/**
	 * Constructor.
	 * @param name the name of the formatter
	 * @param formatterClassName the class to be used for formatting the value
	 * @param args args to configure the formatter
	 */
	public FormatterDefinition(final String name, final String formatterClassName, final String args) {
		super(name);
		//---
		Assertion.check().isNotBlank(formatterClassName);
		//---
		this.formatterClassName = formatterClassName;
		this.args = args;
	}

	public String getFormatterClassName() {
		return formatterClassName;
	}

	public String getArgs() {
		return args;
	}
}

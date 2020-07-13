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
package io.vertigo.studio.plugins.mda.vertigo.domain.ts.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.tools.DefinitionUtil;

/**
 * Model used to define a Domain.
 *
 * @author npiedeloup
 */
public final class TSDomainModel {
	private final DomainSketch domainSketch;

	/***
	 * Constructor.
	 * @param domainSketch Domain
	 */
	TSDomainModel(final DomainSketch domainSketch) {
		Assertion.check().isNotNull(domainSketch);
		//-----
		this.domainSketch = domainSketch;
	}

	/**
	 * @return Type javascript du champ with cardinality
	 */
	public String getTypescriptType() {
		return buildTypescriptType(domainSketch, true);
	}

	/**
	 * @return Name of the domain
	 */
	public String getDomainName() {
		return domainSketch.getName();
	}

	/**
	 * @return Local name of the domain
	 */
	public String getDomainDefinitionName() {
		return DefinitionUtil.getLocalName("St" + domainSketch.getDtDefinitionName(), DtSketch.PREFIX);
	}

	/**
	 * @return Simple TS type
	 */
	public String getDomainTypeName() {
		return buildTypescriptType(domainSketch, false);
	}

	/**
	 * @return True si le type est une primitive.
	 */
	public boolean isPrimitive() {
		return domainSketch.getScope().isPrimitive();
	}

	/**
	 * Returns the javascript type.
	 * @param  domainSketch DtDomain
	 * @return String
	 */
	private static String buildTypescriptType(final DomainSketch domainSketch, final boolean multiple) {
		if (domainSketch.getScope().isPrimitive()) {
			final BasicType dataType = domainSketch.getDataType();
			if (dataType.isNumber()) {
				return "number";
			} else if (dataType == BasicType.Boolean) {
				return "boolean";
			}
			return "string";
		} else if (domainSketch.getScope().isValueObject()) {
			return DomainUtil.getSimpleNameFromCanonicalName(domainSketch.getValueObjectClassName());
		}
		return DefinitionUtil.getLocalName("St" + domainSketch.getDtDefinitionName(), DtSketch.PREFIX) + ((multiple) ? "[]" : "");
	}
}

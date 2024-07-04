/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.notebook.task;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.domain.DomainSketch;

/**
 * Attribut d'une tache.
 * Il s'agit soit :
 *  - d'un type primitif
 *  - d'un type complexe : DTO ou DTC
 * Dans tous les cas il s'agit d'un {@link io.vertigo.studio.notebook.domain.DomainSketch}.
 *
 * Le paramètre peut être :
 *
 *  - obligatoire ou facultatif
 *
 * @author  fconstantin, pchretien
 */
public final class TaskSketchAttribute {
	/** Name of the attribute. */
	private final String name;

	private final DomainSketch domainSketch;

	/** if the attribute cardinality. */
	private final Cardinality cardinality;

	/**
	 * Constructor.
	 *
	 * @param attributeName the name of the attribute
	 * @param domainSketch the domain of the attribute
	 * @param required if the attribute is required
	 */
	TaskSketchAttribute(final String attributeName, final DomainSketch domainSketch, final Cardinality cardinality) {
		Assertion.check()
				.isNotBlank(attributeName)
				.isNotNull(cardinality)
				.isTrue(StringUtil.isLowerCamelCase(attributeName), "the name of the attribute {0} must be in lowerCamelCase", attributeName)
				.isNotNull(domainSketch);
		//-----
		name = attributeName;
		this.domainSketch = domainSketch;
		this.cardinality = cardinality;
	}

	/**
	 * @return the name of the attribute.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Domain the domain
	 */
	public DomainSketch getDomain() {
		return domainSketch;
	}

	/**
	 * @return if the attribute cardinality
	 */
	public Cardinality getCardinality() {
		return cardinality;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "{ name : " + name + ", smarttype :" + domainSketch + ", cardinality :" + cardinality + "]";
	}

}

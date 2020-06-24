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

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.core.lang.Builder;
import io.vertigo.studio.metamodel.domain.Domain.Scope;

/**
 * This class must be used to build a Domain.
 * @author pchretien, mlaroche
 */
public final class DomainBuilder implements Builder<Domain> {
	private final String myName;
	private final Domain.Scope myScope;

	private final BasicType myDataType;
	private final String myDtDefinitionName;
	private final Class myValueObjectClass;

	/** Formatter. */
	private FormatterDefinition myformatterDefinition;

	/** list of constraints */
	private List<ConstraintDefinition> myConstraintDefinitions;

	/** List of property-value tuples */
	private Properties myProperties;

	/**
	 * Constructor.
	 * @param name the name of the domain
	 * @param dataType the dataType of the domain
	 */
	DomainBuilder(final String name, final BasicType dataType) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(dataType);
		//---
		myName = name;
		myScope = Domain.Scope.PRIMITIVE;

		myDataType = dataType;
		myDtDefinitionName = null;
		myValueObjectClass = null;
	}

	/**
	 * Constructor.
	 * @param name the name of the domain
	 * @param dtDefinitionName the data-object definition of the domain
	 */
	DomainBuilder(final String name, final String dtDefinitionName) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(dtDefinitionName);
		//---
		myName = name;
		myScope = Scope.DATA_OBJECT;

		myDataType = null;
		myDtDefinitionName = dtDefinitionName;
		myValueObjectClass = null;
	}

	/**
	 * Constructor.
	 * @param name the name of the domain
	 * @param valueObjectClass the value-object class of the domain
	 */
	DomainBuilder(final String name, final Class valueObjectClass) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(valueObjectClass);
		//---
		myName = name;
		myScope = Domain.Scope.VALUE_OBJECT;

		myDataType = null;
		myDtDefinitionName = null;
		myValueObjectClass = valueObjectClass;
	}

	/**
	 * @param formatterDefinition the FormatterDefinition
	 * @return this builder
	 */
	public DomainBuilder withFormatter(final FormatterDefinition formatterDefinition) {
		Assertion.check().isNotNull(formatterDefinition);
		//---
		myformatterDefinition = formatterDefinition;
		return this;
	}

	/**
	 * @param constraintDefinitions the list of constraintDefinitions
	 * @return this builder
	 */
	public DomainBuilder withConstraints(final List<ConstraintDefinition> constraintDefinitions) {
		Assertion.check().isNotNull(constraintDefinitions);
		//---
		myConstraintDefinitions = constraintDefinitions;
		return this;
	}

	/**
	* @param properties the properties
	* @return this builder
	*/
	public DomainBuilder withProperties(final Properties properties) {
		Assertion.check().isNotNull(properties);
		//---
		myProperties = properties;
		return this;
	}

	@Override
	public Domain build() {
		return new Domain(
				myName,
				myScope,
				myDataType,
				myDtDefinitionName,
				myValueObjectClass != null ? myValueObjectClass.getCanonicalName() : null,
				myformatterDefinition,
				myConstraintDefinitions == null ? Collections.emptyList() : myConstraintDefinitions,
				myProperties == null ? new Properties() : myProperties);
	}
}

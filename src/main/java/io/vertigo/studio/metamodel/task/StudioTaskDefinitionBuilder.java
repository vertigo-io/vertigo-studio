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
package io.vertigo.studio.metamodel.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.studio.metamodel.domain.Domain;

/**
 * Builder of taskDefinition.
 *
 * @author  fconstantin, pchretien
 */
public final class StudioTaskDefinitionBuilder implements Builder<StudioTaskDefinition> {
	private final List<StudioTaskAttribute> myInTaskAttributes = new ArrayList<>();
	private StudioTaskAttribute myOutTaskAttribute;
	private final String myTaskDefinitionName;
	private String myTaskEngineClassName;
	private String myRequest;
	private String myPackageName;
	private String myDataSpace;

	/**
	 * Constructor.
	 *
	 * @param taskDefinitionName the name of the taskDefinition (TK_XXX_YYY)
	 */
	StudioTaskDefinitionBuilder(final String taskDefinitionName) {
		Assertion.check().notNull(taskDefinitionName);
		//-----
		myTaskDefinitionName = taskDefinitionName;
	}

	/**
	 * Defines the engine, used at runtime to process the task.
	 *
	 * @param taskEngineClassName Class running the task
	 * @return this builder
	 */
	public StudioTaskDefinitionBuilder withEngine(final String taskEngineClassName) {
		Assertion.checkArgNotEmpty(taskEngineClassName);
		//---
		myTaskEngineClassName = taskEngineClassName;
		return this;
	}

	/**
	 * @param request the request used to configure the task. (ldap request, sql request...)
	 * @return this builder
	 */
	public StudioTaskDefinitionBuilder withRequest(final String request) {
		Assertion.check().notNull(request);
		//-----
		//Pour unifier la saisie de la request sous un environnement unix ou dos
		// et pour éviter la disparité de gestion des retours chariot
		//par certains drivers de base de données.
		myRequest = request.replace("\r", "");
		return this;
	}

	/**
	 * @param packageName the name of the package
	 * @return this builder
	 */
	public StudioTaskDefinitionBuilder withPackageName(final String packageName) {
		//packageName peut être null
		//-----
		myPackageName = packageName;
		return this;
	}

	/**
	 * Sets the dataSpace
	 * @param dataSpace the dataSpace
	 * @return this builder
	 */
	public StudioTaskDefinitionBuilder withDataSpace(final String dataSpace) {
		//dataSpace can be null
		//-----
		myDataSpace = dataSpace;
		return this;
	}

	/**
	 * Adds an input attribute.
	 *
	 * @param attributeName the name of the attribute
	 * @param domain the domain of the attribute
	 * @param cardinality cadinality (one, optional, many)
	 * @return this builder
	 */
	public StudioTaskDefinitionBuilder addInAttribute(final String attributeName, final Domain domain, final Cardinality cardinality) {
		Assertion.check()
				.notNull(attributeName)
				.notNull(domain)
				.notNull(cardinality);
		//-----
		final StudioTaskAttribute taskAttribute = new StudioTaskAttribute(attributeName, domain, cardinality);
		myInTaskAttributes.add(taskAttribute);
		return this;
	}

	/**
	 * Adds an output attribute.
	 *
	 * @param attributeName the name of the attribute
	 * @param domain the domain of the attribute
	 * @param cardinality cadinality (one, optional, many)
	 * @return this builder
	 */
	public StudioTaskDefinitionBuilder withOutAttribute(final String attributeName, final Domain domain, final Cardinality cardinality) {
		//-----
		myOutTaskAttribute = new StudioTaskAttribute(attributeName, domain, cardinality);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public StudioTaskDefinition build() {
		return new StudioTaskDefinition(
				myTaskDefinitionName,
				myPackageName,
				myDataSpace == null ? "main" : myDataSpace,
				myTaskEngineClassName,
				myRequest,
				myInTaskAttributes,
				Optional.ofNullable(myOutTaskAttribute));
	}

}

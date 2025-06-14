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
package io.vertigo.studio.notebook.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.studio.notebook.domain.DomainSketch;

/**
 * Builder of taskSketch.
 *
 * @author fconstantin, pchretien
 */
public final class TaskSketchBuilder implements Builder<TaskSketch> {

	private final List<TaskSketchAttribute> myInTaskAttributes = new ArrayList<>();
	private TaskSketchAttribute myOutTaskAttribute;
	private final String myTaskSketchName;
	private String myTaskEngineClassName;
	private String myRequest;
	private String myPackageName;
	private String myDataSpace;

	/**
	 * Constructor.
	 *
	 * @param taskSketchName the name of the taskSketch (TK_XXX_YYY)
	 */
	TaskSketchBuilder(final String taskSketchName) {
		Assertion.check().isNotNull(taskSketchName);
		//-----
		myTaskSketchName = taskSketchName;
	}

	/**
	 * Defines the engine, used at runtime to process the task.
	 *
	 * @param taskEngineClassName Class running the task
	 * @return this builder
	 */
	public TaskSketchBuilder withEngine(final String taskEngineClassName) {
		Assertion.check().isNotBlank(taskEngineClassName);
		//---
		myTaskEngineClassName = taskEngineClassName;
		return this;
	}

	/**
	 * @param request the request used to configure the task. (ldap request, sql request...)
	 * @return this builder
	 */
	public TaskSketchBuilder withRequest(final String request) {
		Assertion.check().isNotNull(request);
		//-----
		// Now we keep the request as it is (before we replaced \r\n by \n).
		// Because we use multiline string, we can't change cariage return.
		// If we do, git we replace it when file is downloaded and file is marked as modified
		myRequest = request;
		return this;
	}

	/**
	 * @param packageName the name of the package
	 * @return this builder
	 */
	public TaskSketchBuilder withPackageName(final String packageName) {
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
	public TaskSketchBuilder withDataSpace(final String dataSpace) {
		//dataSpace can be null
		//-----
		myDataSpace = dataSpace;
		return this;
	}

	/**
	 * Adds an input attribute.
	 *
	 * @param attributeName the name of the attribute
	 * @param domainSketch the domain of the attribute
	 * @param cardinality cadinality (one, optional, many)
	 * @return this builder
	 */
	public TaskSketchBuilder addInAttribute(final String attributeName, final DomainSketch domainSketch, final Cardinality cardinality) {
		final TaskSketchAttribute taskAttribute = new TaskSketchAttribute(attributeName, domainSketch, cardinality);
		myInTaskAttributes.add(taskAttribute);
		return this;
	}

	/**
	 * Adds an output attribute.
	 *
	 * @param attributeName the name of the attribute
	 * @param domainSketch the domain of the attribute
	 * @param cardinality cadinality (one, optional, many)
	 * @return this builder
	 */
	public TaskSketchBuilder withOutAttribute(final String attributeName, final DomainSketch domainSketch, final Cardinality cardinality) {
		//-----
		myOutTaskAttribute = new TaskSketchAttribute(attributeName, domainSketch, cardinality);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public TaskSketch build() {
		return new TaskSketch(
				myTaskSketchName,
				myPackageName,
				myDataSpace == null ? "main" : myDataSpace,
				myTaskEngineClassName,
				myRequest,
				myInTaskAttributes,
				Optional.ofNullable(myOutTaskAttribute));
	}

}

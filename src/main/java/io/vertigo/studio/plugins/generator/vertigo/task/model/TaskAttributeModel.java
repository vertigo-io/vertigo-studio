/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.task.model;

import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.task.TaskSketchAttribute;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;

/**
 * Génération des classes/méthodes des taches de type DAO.
 *
 * @author pchretien, mlaroche
 */
public final class TaskAttributeModel {
	private final TaskSketchAttribute taskAttribute;
	private final String javaType;
	private final String javaTypeLabel;

	TaskAttributeModel(final TaskSketchAttribute taskAttribute, final Function<String, String> classNameFromDt) {
		Assertion.check()
				.isNotNull(taskAttribute)
				.isNotNull(classNameFromDt);
		//-----
		this.taskAttribute = taskAttribute;
		//---
		javaType = String.valueOf(DomainUtil.buildJavaType(taskAttribute, classNameFromDt));
		javaTypeLabel = DomainUtil.buildJavaTypeLabel(taskAttribute, classNameFromDt);

	}

	/**
	 * @return Nom de l'attribut.
	 */
	public String getName() {
		return taskAttribute.getName();
	}

	/**
	 * @return Nom de la variable
	 */
	public String getVariableName() {
		return taskAttribute.getName();
	}

	/**
	 * @return Type de la donnée en string
	 */
	public String getDataType() {
		return javaType;
	}

	/**
	 * @return Type java du champ
	 */
	public String getJavaTypeLabel() {
		return javaTypeLabel;
	}

	/**
	 * @return Si l'attribut est obligatoire.
	 */
	public boolean isOptionalOrNullable() {
		return taskAttribute.getCardinality().isOptionalOrNullable();
	}

	/**
	 * @return Domain.
	 */
	public DomainSketch getDomain() {
		return taskAttribute.getDomain();
	}

	public String getSmartTypeName() {
		return "STy" + taskAttribute.getDomain().getLocalName();
	}
}

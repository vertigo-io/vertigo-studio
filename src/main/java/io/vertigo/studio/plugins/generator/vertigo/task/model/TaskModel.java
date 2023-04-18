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
package io.vertigo.studio.plugins.generator.vertigo.task.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.task.TaskSketch;
import io.vertigo.studio.notebook.task.TaskSketchAttribute;

/**
 * Génération des classes/méthodes des taches de type DAO.
 *
 * @author pchretien, mlaroche
 */
public final class TaskModel {
	private final TaskSketch taskSketch;
	private final List<TaskAttributeModel> ins = new ArrayList<>();

	private final TaskAttributeModel out;
	private final boolean optional;

	public TaskModel(final TaskSketch taskSketch, final Function<String, String> classNameFromDt) {
		Assertion.check()
				.isNotNull(taskSketch)
				.isNotNull(classNameFromDt);
		//-----
		this.taskSketch = taskSketch;
		boolean hasOption = false;

		for (final TaskSketchAttribute attribute : taskSketch.getInAttributes()) {
			final TaskAttributeModel templateTaskAttribute = new TaskAttributeModel(attribute, classNameFromDt);
			ins.add(templateTaskAttribute);
			hasOption = hasOption || attribute.getCardinality().isOptionalOrNullable();
		}

		if (taskSketch.getOutAttributeOpt().isPresent()) {
			final TaskSketchAttribute attribute = taskSketch.getOutAttributeOpt().get();
			final TaskAttributeModel taskAttributeModel = new TaskAttributeModel(attribute, classNameFromDt);
			//On est dans le cas des paramètres OUT
			out = taskAttributeModel;
			hasOption = hasOption || attribute.getCardinality().isOptionalOrNullable();
		} else {
			out = null;
		}
		optional = hasOption;
	}

	/**
	 * @return Name of taskDefinition
	 */
	public String getName() {
		return taskSketch.getKey().getName();
	}

	/**
	 * @return Name of taskDefinition
	 */
	public String getTaskName() {
		return taskSketch.getTaskName();
	}

	/**
	 * @return Nom de la méthode en CamelCase
	 */
	public String getMethodName() {
		// Nom de la définition sans prefix (XxxYyyy).
		return StringUtil.first2LowerCase(taskSketch.getLocalName());
	}

	/**
	 * @return Liste des attributs en entréee
	 */
	public List<TaskAttributeModel> getInAttributes() {
		return ins;
	}

	/**
	 * @return Si la méthode possède un type de retour (sinon void)
	 */
	public boolean isOut() {
		return out != null;
	}

	/**
	 * @return Attribut de sortie (Unique)
	 */
	public TaskAttributeModel getOutAttribute() {
		Assertion.check().isNotNull(out);
		//-----
		return out;
	}

	/**
	 * @return Attribut de sortie (Unique)
	 */
	public String getRequest() {
		return taskSketch.getRequest();
	}

	/**
	 * @return Attribut de sortie (Unique)
	 */
	public String getTaskEngineClass() {
		return taskSketch.getTaskEngineClassName();
	}

	/**
	 * @return Si cette task utilise vertigo.core.lang.Option
	 */
	public boolean hasOptions() {
		return optional;
	}

	/**
	 * @return Si cette task est liée à un dataspace spécifique (différent de main)
	 */
	public boolean hasSpecificDataSpace() {
		return !"main".equals(taskSketch.getDataSpace());
	}

	/**
	 * @return Si cette task est liée à un dataspace spécifique (différent de main)
	 */
	public String getDataSpace() {
		return taskSketch.getDataSpace();
	}
}

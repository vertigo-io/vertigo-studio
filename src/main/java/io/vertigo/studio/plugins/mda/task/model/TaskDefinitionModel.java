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
package io.vertigo.studio.plugins.mda.task.model;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.DefinitionUtil;
import io.vertigo.core.util.StringUtil;
import io.vertigo.datastore.entitystore.EntityStoreManager;
import io.vertigo.dynamo.task.metamodel.StudioTaskAttribute;
import io.vertigo.dynamo.task.metamodel.StudioTaskDefinition;

/**
 * Génération des classes/méthodes des taches de type DAO.
 *
 * @author pchretien
 */
public final class TaskDefinitionModel {
	private final StudioTaskDefinition taskDefinition;
	private final List<TaskAttributeModel> ins = new ArrayList<>();

	private final TaskAttributeModel out;
	private final boolean optional;

	public TaskDefinitionModel(final StudioTaskDefinition taskDefinition) {
		Assertion.checkNotNull(taskDefinition);
		//-----
		this.taskDefinition = taskDefinition;
		boolean hasOption = false;

		for (final StudioTaskAttribute attribute : taskDefinition.getInAttributes()) {
			final TaskAttributeModel templateTaskAttribute = new TaskAttributeModel(attribute);
			ins.add(templateTaskAttribute);
			hasOption = hasOption || attribute.getCardinality().isOptionalOrNullable();
		}

		if (taskDefinition.getOutAttributeOption().isPresent()) {
			final StudioTaskAttribute attribute = taskDefinition.getOutAttributeOption().get();
			final TaskAttributeModel templateTaskAttribute = new TaskAttributeModel(attribute);
			//On est dans le cas des paramètres OUT
			out = templateTaskAttribute;
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
		return taskDefinition.getName();
	}

	/**
	 * @return Name of taskDefinition
	 */
	public String getTaskName() {
		return taskDefinition.getTaskName();
	}

	/**
	 * @return Nom de la méthode en CamelCase
	 */
	public String getMethodName() {
		// Nom de la définition sans prefix (XxxYyyy).
		final String localName = DefinitionUtil.getLocalName(taskDefinition.getName(), StudioTaskDefinition.class);
		return StringUtil.first2LowerCase(localName);
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
		Assertion.checkNotNull(out);
		//-----
		return out;
	}

	/**
	 * @return Attribut de sortie (Unique)
	 */
	public String getRequest() {
		return taskDefinition.getRequest()
				.replaceAll("\"", "\\\\\"")
				.replaceAll("\\n", "\" + \n \"");
	}

	/**
	 * @return Attribut de sortie (Unique)
	 */
	public String getTaskEngineClass() {
		return taskDefinition.getTaskEngineClass().getName();
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
		return !EntityStoreManager.MAIN_DATA_SPACE_NAME.equals(taskDefinition.getDataSpace());
	}

	/**
	 * @return Si cette task est liée à un dataspace spécifique (différent de main)
	 */
	public String getDataSpace() {
		return taskDefinition.getDataSpace();
	}
}

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
package io.vertigo.studio.notebook.task;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;

/**
 * This class defines a task and its attributes.
 *
 * @author  fconstantin, pchretien
 */
@SkecthPrefix(TaskSketch.PREFIX)
public final class TaskSketch extends AbstractSketch {
	public static final String PREFIX = "Tk";

	/** the package name. */
	private final String packageName;

	/** the dataSpace. */
	private final String dataSpace;

	/** Chaine de configuration du service. */
	private final String request;

	/** Map des (Nom, TaskAttribute) définissant les attributs de tache. */
	private final Map<String, TaskSketchAttribute> inTaskAttributes;

	private final Optional<TaskSketchAttribute> outTaskAttributeOpt;

	/**
	 * Moyen de réaliser la tache.
	 */
	private final String taskEngineClassName;

	/**
	 * Constructor.
	 * @param taskEngineClassName Classe réalisant l'implémentation
	 * @param request Chaine de configuration
	 */
	TaskSketch(
			final String name,
			final String packageName,
			final String dataSpace,
			final String taskEngineClassName,
			final String request,
			final List<TaskSketchAttribute> inTaskAttributes,
			final Optional<TaskSketchAttribute> outTaskAttributeOpt) {
		super(name);
		//---
		Assertion.check()
				.isNotBlank(dataSpace)
				.isNotBlank(taskEngineClassName, "a taskEngineClass is required")
				.isNotBlank(request, "a request is required")
				.isNotNull(inTaskAttributes)
				.isNotNull(outTaskAttributeOpt);
		//-----
		this.packageName = packageName;
		this.dataSpace = dataSpace;
		this.request = request;
		this.inTaskAttributes = createMap(inTaskAttributes);
		this.outTaskAttributeOpt = outTaskAttributeOpt;
		this.taskEngineClassName = taskEngineClassName;
	}

	/**
	 * Static method factory for TaskDefinition
	 * @param taskDefinitionName the name of the taskDefinition (TK_XXX_YYY)
	 * @return TaskDefinition
	 */
	public static TaskSketchBuilder builder(final String taskDefinitionName) {
		return new TaskSketchBuilder(taskDefinitionName);
	}

	/**
	 * Création  d'une Map non modifiable.
	 * @param taskAttributes Attributs de la tache
	 */
	private static Map<String, TaskSketchAttribute> createMap(final List<TaskSketchAttribute> taskAttributes) {
		final Map<String, TaskSketchAttribute> map = new LinkedHashMap<>();
		for (final TaskSketchAttribute taskAttribute : taskAttributes) {
			Assertion.check()
					.isNotNull(taskAttribute)
					.isFalse(map.containsKey(taskAttribute.getName()), "attribut {0} existe déjà", taskAttribute.getName());
			//-----
			map.put(taskAttribute.getName(), taskAttribute);
		}
		return java.util.Collections.unmodifiableMap(map);
	}

	/**
	 * Retourne la classe réalisant l'implémentation de la tache.
	 *
	 * @return Classe réalisant l'implémentation
	 */
	public String getTaskEngineClassName() {
		return taskEngineClassName;
	}

	/**
	 * Returns the dataSpace to which the taskDefinition belongs.
	 *
	 * @return the dataSpace.
	 */
	public String getDataSpace() {
		return dataSpace;
	}

	/**
	 * Retourne la String de configuration de la tache.
	 * Cette méthode est utilisée par le TaskEngine.
	 *
	 * @return Configuration de la tache.
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * Retourne l' attribut OUT
	 *
	 * @return Attribut OUT
	 */
	public Optional<TaskSketchAttribute> getOutAttributeOpt() {
		return outTaskAttributeOpt;
	}

	/**
	 * Retourne la liste des attributs IN
	 *
	 * @return Liste des attributs IN
	 */
	public Collection<TaskSketchAttribute> getInAttributes() {
		return inTaskAttributes.values();
	}

	/**
	 * @return Nom du package
	 */
	public String getPackageName() {
		return packageName;
	}

	public String getTaskName() {
		return PREFIX + getLocalName();
	}
}

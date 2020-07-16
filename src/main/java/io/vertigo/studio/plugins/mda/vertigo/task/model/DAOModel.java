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
package io.vertigo.studio.plugins.mda.vertigo.task.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.task.TaskSketch;

/**
 * Objet utilisé par FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class DAOModel {
	private final DtSketch dtSketch;
	private final String packageName;
	private final Collection<TaskDefinitionModel> taskDefinitions = new ArrayList<>();

	private final boolean hasOptions;

	/**
	 * Constructeur.
	 *
	 * @param dtSketch DtDefinition de l'objet à générer
	 */
	public DAOModel(final MdaConfig mdaConfig, final DtSketch dtSketch, final Collection<TaskSketch> taskDefinitionCollection, final Function<String, String> classNameFromDt) {
		Assertion.check()
				.isNotNull(mdaConfig)
				.isNotNull(dtSketch)
				.isNotNull(taskDefinitionCollection);
		final String definitionPackageName = dtSketch.getPackageName();
		final String packageNamePrefix = mdaConfig.getProjectPackageName();
		// ---
		Assertion.check()
				.isTrue(definitionPackageName.startsWith(packageNamePrefix), "Package name {0}, must begin with normalised prefix: {1}", definitionPackageName, packageNamePrefix)
				.isTrue(definitionPackageName.substring(packageNamePrefix.length()).contains(".domain"), "Package name {0}, must contains the modifier .domain", definitionPackageName);
		// ---
		//we need to find the featureName, aka between projectpackageName and .domain
		final String featureName = definitionPackageName.substring(packageNamePrefix.length(), definitionPackageName.indexOf(".domain"));
		if (!StringUtil.isBlank(featureName)) {
			Assertion.check().isTrue(featureName.lastIndexOf('.') == 0, "The feature {0} must not contain any dot", featureName.substring(1));
		}
		// the subpackage is what's behind the .domain
		final String subpackage = definitionPackageName.substring(definitionPackageName.indexOf(".domain") + ".domain".length());
		// breaking change -> need to redefine what's the desired folder structure in javagen...

		this.dtSketch = dtSketch;
		//On construit le nom du package à partir du package de la DT et de la feature.
		packageName = mdaConfig.getProjectPackageName() + featureName + ".dao" + subpackage;

		boolean hasOption = false;
		for (final TaskSketch taskSketch : taskDefinitionCollection) {
			final TaskDefinitionModel templateTaskDefinition = new TaskDefinitionModel(taskSketch, classNameFromDt);
			taskDefinitions.add(templateTaskDefinition);
			hasOption = hasOption || templateTaskDefinition.hasOptions();
		}
		hasOptions = hasOption;
	}

	/**
	 * @return Simple Nom (i.e. sans le package) de la classe d'implémentation du DtObject
	 */
	public String getClassSimpleName() {
		return dtSketch.getClassSimpleName() + "DAO";
	}

	/**
	 * @return Si l'entité est un keyConcept
	 */
	public boolean isKeyConcept() {
		return dtSketch.getStereotype() == StudioStereotype.KeyConcept;
	}

	/**
	 * @return Type de la PK
	 */
	public String getIdFieldType() {
		Assertion.check().isTrue(dtSketch.getIdField().get().getDomain().getScope().isPrimitive(), "An id field should be a primitive");
		//---
		return dtSketch.getIdField().get().getDomain().getDataType().getJavaClass().getCanonicalName();
	}

	/**
	 * @return Nom de la classe du Dt
	 */
	public String getDtClassCanonicalName() {
		return dtSketch.getClassCanonicalName();
	}

	/**
	 * @return Nom simple de la classe du Dt
	 */
	public String getDtClassSimpleName() {
		return dtSketch.getClassSimpleName();
	}

	/**
	 * @return Nom du package
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @return Liste des tasks
	 */
	public Collection<TaskDefinitionModel> getTaskDefinitions() {
		return taskDefinitions;
	}

	/**
	 * @return Si ce dao utilise au moins une option : vertigo.core.lang.Option
	 */
	public boolean isOptions() {
		return hasOptions;
	}

}

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
package io.vertigo.studio.plugins.generator.vertigo.domain.ts.model;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.DtSketchField.FieldType;

/**
 * Model used by FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class TSStudioDtModel {

	private final DtSketch dtSketch;
	private final List<TSStudioDtFieldModel> dtFieldModels;
	private final Set<TSDomainModel> domainModels;

	/**
	 * Constructeur.
	 *
	 * @param dtSketch DtDefinition de l'objet à générer
	 */
	public TSStudioDtModel(final DtSketch dtSketch) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.dtSketch = dtSketch;

		dtFieldModels = dtSketch.getFields()
				.stream()
				.filter(dtField -> FieldType.COMPUTED != dtField.getType())
				.map(TSStudioDtFieldModel::new)
				.toList();

		domainModels = dtSketch.getFields()
				.stream()
				.filter(dtField -> FieldType.COMPUTED != dtField.getType())
				.map(DtSketchField::getDomain)
				.distinct()
				.map(TSDomainModel::new)
				.collect(Collectors.toSet());
	}

	/**
	 * @return DT definition
	 */
	public DtSketch getDtDefinition() {
		return dtSketch;
	}

	/**
	 * @return Simple Nom (i.e. sans le package) de la classe d'implementation du DtObject
	 */
	public String getClassSimpleName() {
		return dtSketch.getClassSimpleName();
	}

	/**
	 * @return true si au moins un champ est de type primitif.
	 */
	public boolean isContainsPrimitiveField() {
		return getFields()
				.stream()
				.anyMatch(TSStudioDtFieldModel::isPrimitive);
	}

	/**
	 * @return true si au moins un champ est de type List.
	 */
	public boolean isContainsListOfObjectField() {
		return getFields()
				.stream()
				.anyMatch(TSStudioDtFieldModel::isListOfObject);
	}

	/**
	 * @return true si au moins un champ est de type Object (au sens TS).
	 */
	public boolean isContainsObjectField() {
		return getFields()
				.stream()
				.anyMatch(TSStudioDtFieldModel::isObject);
	}

	/**
	 * @return Nom du fichier de la classe normalisÃ© (AAA_BBB_CCC => aaa-bbb-ccc).
	 */
	public String getJsClassFileName() {
		return dtSketch.getLocalName().toLowerCase(Locale.ENGLISH).replace('_', '-');
	}

	/**
	 * @return Nom du package
	 */
	public String getFunctionnalPackageName() {
		final String[] splittedPackage = dtSketch.getPackageName().split("\\.");
		if (splittedPackage.length > 1) {
			return splittedPackage[splittedPackage.length - 1];
		}
		return dtSketch.getPackageName();

	}

	/**
	 * @return Liste de champs
	 */
	public List<TSStudioDtFieldModel> getFields() {
		return dtFieldModels;
	}

	/**
	 * @return Liste de domains
	 */
	public Set<TSDomainModel> getDomains() {
		return domainModels;
	}
}

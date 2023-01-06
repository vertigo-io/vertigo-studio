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
package io.vertigo.studio.plugins.generator.mermaid.model;

import java.util.Optional;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;

/**
 * Model used to define a DtField.
 *
 * @author mlaroche
 */
public final class MermaidDtFieldModel {
	private final DtSketchField dtField;
	private final String mermaidType;
	private final Optional<String> dataObjectClassNameOpt;

	/***
	 * Constructeur.
	 * @param dtField Champ à générer
	 */
	MermaidDtFieldModel(
			final DtSketch dtSketch,
			final DtSketchField dtField,
			final Function<String, String> classNameFromDt) {
		Assertion.check()
				.isNotNull(dtSketch)
				.isNotNull(dtField)
				.isNotNull(classNameFromDt);
		//-----
		this.dtField = dtField;
		if (dtField.getDomain().getScope().isDataObject()) {
			dataObjectClassNameOpt = Optional.of(classNameFromDt.apply(dtField.getDomain().getDtSketchKey().getName()));
		} else {
			dataObjectClassNameOpt = Optional.empty();
		}
		//---
		mermaidType = buildMermaidType(dtField.getDomain(), classNameFromDt, dtField.getCardinality());
	}

	/**
	 * Nom du champ en camelCase.
	 * En freemarker pour upperCamelCase : myString?cap_first
	 * @return unNom
	 */
	public String getName() {
		return dtField.getName();
	}

	/**
	 * @return DtField
	 */
	public DtSketchField getSource() {
		return dtField;
	}

	public String getResourceKey() {
		return dtField.getResourceKey();
	}

	public String getLabel() {
		return dtField.getLabel().getDisplay();
	}

	/**
	 * @return Type du champ (
	 */
	public String getType() {
		return dtField.getType().name();
	}

	/**
	 * @return Type java du champ
	 */
	public String getMermaidType() {
		return mermaidType;
	}

	/**
	 * @return Si la propriété est requise
	 */
	public boolean isRequired() {
		return dtField.getCardinality().hasOne();
	}

	/**
	 * @return Si la propriété est multiple
	 */
	public boolean isMultiple() {
		return dtField.getCardinality().hasMany();
	}

	/**
	 * @return Si la propriété vise un autre type de dt
	 */
	public boolean isDataObject() {
		return dtField.getDomain().getScope().isDataObject();
	}

	/**
	 * @return Le nom de la classe du dt visé
	 */
	public String getDataObjectClassName() {
		return dataObjectClassNameOpt.get();
	}

	private static String buildMermaidType(final DomainSketch domainSketch, final Function<String, String> classNameFromDt, final Cardinality cardinality) {
		final String className = DomainUtil.buildJavaTypeName(domainSketch, classNameFromDt);
		if (cardinality.hasMany()) {
			final String manyTargetClassName = DomainUtil.getManyTargetJavaClass(domainSketch);
			return manyTargetClassName + '~' + className + '~';
		}
		return className;
	}

}

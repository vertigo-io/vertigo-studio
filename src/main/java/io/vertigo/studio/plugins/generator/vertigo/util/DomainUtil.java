/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.notebook.task.TaskSketchAttribute;

/**
 * Helper.
 *
 * @author emangin
 */
public final class DomainUtil {

	/**
	 * Constructeur privé pour classe utilitaire.
	 */
	private DomainUtil() {
		//RAS
	}

	/**
	 * Construite le type java (sous forme de chaine de caractère) correspondant
	 * à un champ.
	 * @param dtField the field
	 * @return String
	 */
	public static String buildJavaType(final DtSketchField dtField, final Function<String, String> classNameFromDt) {
		return buildJavaType(dtField.getDomain(), classNameFromDt, dtField.getCardinality(), getManyTargetJavaClass(dtField.getDomain()));
	}

	/**
	 * Construite le type java (sous forme de chaine de caractère) correspondant
	 * à un attribut de tache.
	 * @param taskAttribute the attribute
	 * @return String
	 */
	public static String buildJavaType(final TaskSketchAttribute taskAttribute, final Function<String, String> classNameFromDt) {
		return buildJavaType(taskAttribute.getDomain(), classNameFromDt, taskAttribute.getCardinality(), getManyTargetJavaClass(taskAttribute.getDomain()));
	}

	/**
	 * Construite le label du type java (sous forme de chaine de caractère) correspondant
	 * à un DtField.
	 * @param dtField DtField
	 * @return String
	 */
	public static String buildJavaTypeLabel(final DtSketchField dtField, final Function<String, String> classNameFromDt) {
		return buildJavaTypeLabel(dtField.getDomain(), classNameFromDt, dtField.getCardinality(), getManyTargetJavaClass(dtField.getDomain()));
	}

	/**
	 * Construite le label du type java (sous forme de chaine de caractère) correspondant
	 * à un attribut de tache.
	 * @param taskAttribute the attribute
	 * @return String
	 */
	public static String buildJavaTypeLabel(final TaskSketchAttribute taskAttribute, final Function<String, String> classNameFromDt) {
		return buildJavaTypeLabel(taskAttribute.getDomain(), classNameFromDt, taskAttribute.getCardinality(), getManyTargetJavaClass(taskAttribute.getDomain()));
	}

	public static String getManyTargetJavaClass(final DomainSketch domainSketch) {
		switch (domainSketch.getScope()) {
			case DATA_OBJECT:
				return "io.vertigo.datamodel.structure.model.DtList";
			case PRIMITIVE:
			case VALUE_OBJECT:
				return List.class.getName();
			default:
				throw new IllegalStateException();
		}
	}

	public static String buildJavaTypeName(final DomainSketch domainSketch, final Function<String, String> classNameFromDt) {
		final String className;
		switch (domainSketch.getScope()) {
			case PRIMITIVE:
				String javaType = domainSketch.getDataType().getJavaClass().getName();

				//On simplifie l'écriture des types primitifs
				//java.lang.String => String
				if (javaType.startsWith("java.lang.")) {
					javaType = javaType.substring("java.lang.".length());
				}
				className = javaType;
				break;
			case DATA_OBJECT:
				className = classNameFromDt.apply(domainSketch.getDtSketchKey().getName());
				break;
			case VALUE_OBJECT:
				className = domainSketch.getValueObjectClassName();
				break;
			default:
				throw new IllegalStateException();
		}
		return className;
	}

	private static String buildJavaType(final DomainSketch domainSketch, final Function<String, String> classNameFromDt, final Cardinality cardinality, final String manyTargetClassName) {
		final String className = buildJavaTypeName(domainSketch, classNameFromDt);
		if (cardinality.hasMany()) {
			return manyTargetClassName + '<' + className + '>';
		}
		return className;
	}

	public static String buildJavaTypeLabel(final DomainSketch domainSketch, final Function<String, String> classNameFromDt, final Cardinality cardinality, final String manyTargetClassName) {
		final String classLabel;
		switch (domainSketch.getScope()) {
			case PRIMITIVE:
				classLabel = domainSketch.getDataType().getJavaClass().getSimpleName();
				break;
			case DATA_OBJECT:
				classLabel = getSimpleNameFromCanonicalName(classNameFromDt.apply(domainSketch.getDtSketchKey().getName()));
				break;
			case VALUE_OBJECT:
				classLabel = getSimpleNameFromCanonicalName(domainSketch.getValueObjectClassName());
				break;
			default:
				throw new IllegalStateException();
		}
		if (cardinality.hasMany()) {
			return manyTargetClassName.substring(manyTargetClassName.lastIndexOf('.') + 1) + " de " + classLabel;
		}
		return classLabel;
	}

	public static Collection<DtSketch> getDtSketchs(final Notebook notebook) {
		return sortSketchCollection(notebook.getAll(DtSketch.class));
	}

	public static Map<String, Collection<DtSketch>> getDtSketchCollectionMap(final Notebook notebook) {
		return getSketchCollectionMap(getDtSketchs(notebook));
	}

	public static Collection<AssociationSimpleSketch> getSimpleAssociations(final Notebook notebook) {
		return sortAssociationsCollection(notebook.getAll(AssociationSimpleSketch.class));
	}

	public static Collection<AssociationNNSketch> getNNAssociations(final Notebook notebook) {
		return sortAssociationsCollection(notebook.getAll(AssociationNNSketch.class));
	}

	/**
	 * trie de la collection.
	 * @param sketchCollection collection à trier
	 * @return collection triée
	 */
	public static List<DtSketch> sortSketchCollection(final Collection<DtSketch> sketchCollection) {
		return sketchCollection
				.stream()
				.sorted(Comparator.comparing(dt -> dt.getKey().getName()))
				.collect(Collectors.toList());
	}

	/**
	 * @param definitionCollection collection à traiter
	 * @return map ayant le package name en clef
	 */
	private static Map<String, Collection<DtSketch>> getSketchCollectionMap(final Collection<DtSketch> dtSketchs) {
		final Map<String, Collection<DtSketch>> map = new LinkedHashMap<>();

		for (final DtSketch dtSketch : dtSketchs) {
			map.computeIfAbsent(dtSketch.getPackageName(),
					k -> new ArrayList<>())
					.add(dtSketch);
		}
		return map;
	}

	private static <A extends AssociationSketch> Collection<A> sortAssociationsCollection(final Collection<A> associationCollection) {
		return associationCollection
				.stream()
				.sorted(Comparator.comparing(dt -> dt.getKey().getName()))
				.collect(Collectors.toList());
	}

	public static String getSimpleNameFromCanonicalName(final String canonicalClassName) {
		final int lastDot = canonicalClassName.lastIndexOf('.');
		Assertion.check().isTrue(lastDot > 0, "A cananical class name is required, '{0}' does not contain package name", canonicalClassName);
		return canonicalClassName.substring(lastDot + 1);
	}

	public static Function<String, String> createClassNameFromDtFunction(final Notebook notebook) {
		return dtName -> notebook.resolve(dtName, DtSketch.class).getClassCanonicalName();
	}
}

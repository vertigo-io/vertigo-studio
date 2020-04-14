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
package io.vertigo.studio.plugins.mda.vertigo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.studio.metamodel.domain.Domain;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.domain.StudioDtField;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationDefinition;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationNNDefinition;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationSimpleDefinition;
import io.vertigo.studio.metamodel.task.StudioTaskAttribute;

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
	public static String buildJavaType(final StudioDtField dtField, final Function<String, String> classNameFromDt) {
		return buildJavaType(dtField.getDomain(), classNameFromDt, dtField.getCardinality(), getManyTargetJavaClass(dtField.getDomain()));
	}

	/**
	 * Construite le type java (sous forme de chaine de caractère) correspondant
	 * à un attribut de tache.
	 * @param taskAttribute the attribute
	 * @return String
	 */
	public static String buildJavaType(final StudioTaskAttribute taskAttribute, final Function<String, String> classNameFromDt) {
		return buildJavaType(taskAttribute.getDomain(), classNameFromDt, taskAttribute.getCardinality(), getManyTargetJavaClass(taskAttribute.getDomain()));
	}

	/**
	 * Construite le label du type java (sous forme de chaine de caractère) correspondant
	 * à un DtField.
	 * @param dtField DtField
	 * @return String
	 */
	public static String buildJavaTypeLabel(final StudioDtField dtField, final Function<String, String> classNameFromDt) {
		return buildJavaTypeLabel(dtField.getDomain(), classNameFromDt, dtField.getCardinality(), getManyTargetJavaClass(dtField.getDomain()));
	}

	/**
	 * Construite le label du type java (sous forme de chaine de caractère) correspondant
	 * à un attribut de tache.
	 * @param taskAttribute the attribute
	 * @return String
	 */
	public static String buildJavaTypeLabel(final StudioTaskAttribute taskAttribute, final Function<String, String> classNameFromDt) {
		return buildJavaTypeLabel(taskAttribute.getDomain(), classNameFromDt, taskAttribute.getCardinality(), getManyTargetJavaClass(taskAttribute.getDomain()));
	}

	private static String getManyTargetJavaClass(final Domain domain) {
		switch (domain.getScope()) {
			case DATA_OBJECT:
				return "io.vertigo.datamodel.structure.model.DtList";
			case PRIMITIVE:
			case VALUE_OBJECT:
				return List.class.getName();
			default:
				throw new IllegalStateException();
		}
	}

	public static String buildJavaTypeName(final Domain domain, final Function<String, String> classNameFromDt) {
		final String className;
		switch (domain.getScope()) {
			case PRIMITIVE:
				String javaType = domain.getDataType().getJavaClass().getName();

				//On simplifie l'écriture des types primitifs
				//java.lang.String => String
				if (javaType.startsWith("java.lang.")) {
					javaType = javaType.substring("java.lang.".length());
				}
				className = javaType;
				break;
			case DATA_OBJECT:
				className = classNameFromDt.apply("St" + domain.getDtDefinitionName());
				break;
			case VALUE_OBJECT:
				className = domain.getValueObjectClassName();
				break;
			default:
				throw new IllegalStateException();
		}
		return className;
	}

	private static String buildJavaType(final Domain domain, final Function<String, String> classNameFromDt, final Cardinality cardinality, final String manyTargetClassName) {
		final String className = buildJavaTypeName(domain, classNameFromDt);
		if (cardinality.hasMany()) {
			return manyTargetClassName + '<' + className + '>';
		}
		return className;
	}

	public static String buildJavaTypeLabel(final Domain domain, final Function<String, String> classNameFromDt, final Cardinality cardinality, final String manyTargetClassName) {
		final String classLabel;
		switch (domain.getScope()) {
			case PRIMITIVE:
				classLabel = domain.getDataType().getJavaClass().getSimpleName();
				break;
			case DATA_OBJECT:
				classLabel = getSimpleNameFromCanonicalName(classNameFromDt.apply("St" + domain.getDtDefinitionName()));
				break;
			case VALUE_OBJECT:
				classLabel = getSimpleNameFromCanonicalName(domain.getValueObjectClassName());
				break;
			default:
				throw new IllegalStateException();
		}
		if (cardinality.hasMany()) {
			return manyTargetClassName.substring(manyTargetClassName.lastIndexOf('.') + 1) + " de " + classLabel;
		}
		return classLabel;
	}

	public static Collection<StudioDtDefinition> getDtDefinitions(final DefinitionSpace definitionSpace) {
		return sortDefinitionCollection(definitionSpace.getAll(StudioDtDefinition.class));
	}

	public static Map<String, Collection<StudioDtDefinition>> getDtDefinitionCollectionMap(final DefinitionSpace definitionSpace) {
		return getDefinitionCollectionMap(getDtDefinitions(definitionSpace));
	}

	public static Collection<StudioAssociationSimpleDefinition> getSimpleAssociations(final DefinitionSpace definitionSpace) {
		return sortAssociationsCollection(definitionSpace.getAll(StudioAssociationSimpleDefinition.class));
	}

	public static Collection<StudioAssociationNNDefinition> getNNAssociations(final DefinitionSpace definitionSpace) {
		return sortAssociationsCollection(definitionSpace.getAll(StudioAssociationNNDefinition.class));
	}

	/**
	 * trie de la collection.
	 * @param definitionCollection collection à trier
	 * @return collection triée
	 */
	public static List<StudioDtDefinition> sortDefinitionCollection(final Collection<StudioDtDefinition> definitionCollection) {
		final List<StudioDtDefinition> list = new ArrayList<>(definitionCollection);
		list.sort(Comparator.comparing(StudioDtDefinition::getName));
		return list;
	}

	/**
	 * @param definitionCollection collection à traiter
	 * @return map ayant le package name en clef
	 */
	private static Map<String, Collection<StudioDtDefinition>> getDefinitionCollectionMap(final Collection<StudioDtDefinition> definitions) {
		final Map<String, Collection<StudioDtDefinition>> map = new LinkedHashMap<>();

		for (final StudioDtDefinition definition : definitions) {
			map.computeIfAbsent(definition.getPackageName(),
					k -> new ArrayList<>())
					.add(definition);
		}
		return map;
	}

	private static <A extends StudioAssociationDefinition> Collection<A> sortAssociationsCollection(final Collection<A> associationCollection) {
		final List<A> list = new ArrayList<>(associationCollection);
		list.sort(Comparator.comparing(StudioAssociationDefinition::getName));
		return list;
	}

	public static String getSimpleNameFromCanonicalName(final String canonicalClassName) {
		final int lastDot = canonicalClassName.lastIndexOf('.');
		Assertion.checkState(lastDot > 0, "A cananical class name is required, '{0}' does not contain package name", canonicalClassName);
		return canonicalClassName.substring(lastDot + 1);
	}

	public static Function<String, String> createClassNameFromDtFunction(final DefinitionSpace definitionSpace) {
		return dtName -> definitionSpace.resolve(dtName, StudioDtDefinition.class).getClassCanonicalName();
	}
}

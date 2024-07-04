/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.source.vertigo.loaders.java;

import static io.vertigo.studio.plugins.source.vertigo.KspProperty.CARDINALITY;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.DATA_SPACE;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.FK_FIELD_NAME;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.LABEL;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.LABEL_A;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.LABEL_B;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.MULTIPLICITY_A;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.MULTIPLICITY_B;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.NAVIGABILITY_A;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.NAVIGABILITY_B;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.PERSISTENT;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.ROLE_A;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.ROLE_B;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.STEREOTYPE;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.TABLE_NAME;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.ClassSelector;
import io.vertigo.core.lang.ClassSelector.ClassConditions;
import io.vertigo.core.util.ClassUtil;
import io.vertigo.core.util.StringUtil;
import io.vertigo.datamodel.data.definitions.DataField.FieldType;
import io.vertigo.datamodel.data.model.DataObject;
import io.vertigo.datamodel.data.model.DtMasterData;
import io.vertigo.datamodel.data.model.DtStaticMasterData;
import io.vertigo.datamodel.data.model.Entity;
import io.vertigo.datamodel.data.model.Fragment;
import io.vertigo.datamodel.data.model.KeyConcept;
import io.vertigo.datamodel.data.stereotype.DataSpace;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslRawBuilder;
import io.vertigo.studio.impl.source.dsl.raw.DslRawRepository;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.plugins.generator.vertigo.VertigoConstants;
import io.vertigo.studio.plugins.source.vertigo.factories.domain.DomainGrammar;
import io.vertigo.studio.plugins.source.vertigo.loaders.Loader;

/**
 * Lecture des annotations présentes sur les objets métier.
 *
 * @author pchretien, mlaroche
 */
public final class AnnotationLoader implements Loader {
	private static final String DT_DEFINITION_PREFIX = VertigoConstants.VertigoDefinitionPrefix.DtDefinition.getPrefix();

	/**
	 * @return Liste des fichiers Java représentant des objets métiers.
	 */
	private static <F> Set<Class<F>> selectClasses(final String resourcePath, final Class<F> filterClass) {
		final ClassSelector classSelector;
		if (resourcePath.endsWith("*")) {
			//by package
			final String packageName = resourcePath.substring(0, resourcePath.length() - 1);
			classSelector = ClassSelector
					.from(packageName);
		} else {
			//by Iterable of classes
			final Iterable dtDefinitionsClass = ClassUtil.newInstance(resourcePath, Iterable.class);
			Iterator<Class> iterator = dtDefinitionsClass.iterator();
			Set<Class> classes = new HashSet();
			iterator.forEachRemaining(c -> classes.add(c));
			classSelector = ClassSelector
					.from(classes);
		}
		return classSelector
				.filterClasses(ClassConditions.subTypeOf(filterClass))
				.findClasses()
				.stream()
				.map(clazz -> (Class<F>) clazz)
				.collect(Collectors.toSet());
	}

	/** {@inheritDoc} */
	@Override
	public void load(final String resourcePath, final DslRawRepository rawRepository) {
		Assertion.check()
				.isNotBlank(resourcePath)
				.isNotNull(rawRepository);
		//-----
		//--Enregistrement des fichiers java annotés
		for (final Class<DataObject> javaClass : selectClasses(resourcePath, DataObject.class)) {
			load(javaClass, rawRepository);
		}
	}

	private static void load(final Class<DataObject> clazz, final DslRawRepository rawRepository) {
		Assertion.check().isNotNull(rawRepository);
		//-----
		final String simpleName = clazz.getSimpleName();
		final String packageName = clazz.getPackage().getName();
		final String dtDefinitionName = DT_DEFINITION_PREFIX + simpleName;

		String fragmentOf = null;
		if (Fragment.class.isAssignableFrom(clazz)) {
			//Fragments
			for (final Annotation annotation : clazz.getAnnotations()) {
				if (annotation instanceof io.vertigo.datamodel.data.stereotype.Fragment) {
					fragmentOf = ((io.vertigo.datamodel.data.stereotype.Fragment) annotation).fragmentOf();
					break;
				}
			}
			parseFragment(clazz, fragmentOf, dtDefinitionName, packageName, rawRepository);
		} else {
			final StudioStereotype stereotype = parseStereotype(clazz);
			parseDtDefinition(clazz, stereotype, dtDefinitionName, packageName, rawRepository);
		}

	}

	private static void parseFragment(
			final Class<DataObject> clazz,
			final String fragmentOf,
			final String dtName,
			final String packageName,
			final DslRawRepository rawRepository) {
		final DslRawBuilder dtRawBuilder = DslRaw.builder(dtName, DomainGrammar.FRAGMENT_ENTITY)
				.withPackageName(packageName)
				.addRawLink("from", fragmentOf);

		parseRawBuilder(clazz, dtRawBuilder, rawRepository);
	}

	private static void parseDtDefinition(
			final Class<DataObject> clazz,
			final StudioStereotype stereotype,
			final String dtDefinitionName,
			final String packageName,
			final DslRawRepository rawRepository) {
		final DslRawBuilder dtSketchBuilder = DslRaw.builder(dtDefinitionName, DomainGrammar.DT_ENTITY)
				.withPackageName(packageName)
				.addPropertyValue(STEREOTYPE, stereotype.name());

		// Only Persistent stereotypes have a dataspace => Fragment got it from parent
		if (stereotype.isPersistent()) {
			dtSketchBuilder.addPropertyValue(DATA_SPACE, parseDataSpaceAnnotation(clazz));
		}
		parseRawBuilder(clazz, dtSketchBuilder, rawRepository);
	}

	private static void parseRawBuilder(final Class<DataObject> clazz, final DslRawBuilder dtRawBuilder, final DslRawRepository rawRepository) {
		final String packageName = clazz.getPackage().getName();

		// Le tri des champs et des méthodes par ordre alphabétique est important car classe.getMethods() retourne
		// un ordre relativement aléatoire et la lecture des annotations peut donc changer l'ordre
		// des fields d'une lecture à l'autre (ou d'une compilation à l'autre).
		// Cela devient alors bloquant pour une communication par sérialisation entre 2 instances.

		final List<Field> fields = new ArrayList<>(ClassUtil.getAllFields(clazz));
		fields.sort(Comparator.comparing(Field::getName));

		for (final Field field : fields) {
			//On regarde si il s'agit d'un champ
			parseFieldAnnotations(field, dtRawBuilder);
			parseAssociationDefinition(rawRepository, field, packageName);
		}

		final Method[] methods = clazz.getMethods();
		Arrays.sort(methods, Comparator.comparing(Method::getName));
		for (final Method method : methods) {
			parseMethodAnnotations(method, dtRawBuilder);
			//On regarde si il s'agit d'une associations
			parseAssociationDefinition(rawRepository, method, packageName);
		}

		final DslRaw raw = dtRawBuilder.build();
		rawRepository.addRaw(raw);
	}

	private static String parseDataSpaceAnnotation(final Class<?> clazz) {
		final DataSpace[] dataSpaceAnnotations = clazz.getAnnotationsByType(DataSpace.class);
		Assertion.check().isTrue(dataSpaceAnnotations.length <= 1, "Entity {0} can have at max one DataSpace", clazz.getSimpleName());
		// ---
		if (dataSpaceAnnotations.length == 1) {
			return dataSpaceAnnotations[0].value();
		}
		return VertigoConstants.DEFAULT_DATA_SPACE;
	}

	private static StudioStereotype parseStereotype(final Class<DataObject> clazz) {
		if (DtStaticMasterData.class.isAssignableFrom(clazz)) {
			return StudioStereotype.StaticMasterData;
		} else if (DtMasterData.class.isAssignableFrom(clazz)) {
			return StudioStereotype.MasterData;
		} else if (KeyConcept.class.isAssignableFrom(clazz)) {
			return StudioStereotype.KeyConcept;
		} else if (Entity.class.isAssignableFrom(clazz)) {
			return StudioStereotype.Entity;
		}
		return StudioStereotype.ValueObject;
	}

	private static void parseAssociationDefinition(final DslRawRepository rawRepository, final Field field, final String packageName) {
		for (final Annotation annotation : field.getAnnotations()) {
			parseAssociationDefinition(rawRepository, annotation, packageName);
		}
	}

	private static void parseAssociationDefinition(final DslRawRepository rawRepository, final Method method, final String packageName) {
		for (final Annotation annotation : method.getAnnotations()) {
			parseAssociationDefinition(rawRepository, annotation, packageName);
		}
	}

	private static void parseAssociationDefinition(final DslRawRepository rawRepository, final Annotation annotation, final String packageName) {
		if (annotation instanceof io.vertigo.datamodel.data.stereotype.Association) {
			final io.vertigo.datamodel.data.stereotype.Association association = (io.vertigo.datamodel.data.stereotype.Association) annotation;
			//============================================================
			//Attention pamc inverse dans oom les déclarations des objets !!

			final DslRaw associationDefinition = DslRaw.builder(association.name(), DomainGrammar.ASSOCIATION_ENTITY)
					.withPackageName(packageName)
					// associationDefinition.
					//On recherche les attributs (>DtField) de cet classe(>Dt_DEFINITION)
					.addPropertyValue(MULTIPLICITY_A, association.primaryMultiplicity())
					.addPropertyValue(MULTIPLICITY_B, association.foreignMultiplicity())
					// navigabilités
					.addPropertyValue(NAVIGABILITY_A, association.primaryIsNavigable())
					.addPropertyValue(NAVIGABILITY_B, association.foreignIsNavigable())
					//Roles
					.addPropertyValue(ROLE_A, association.primaryRole())
					.addPropertyValue(LABEL_A, association.primaryLabel())
					.addPropertyValue(ROLE_B, association.foreignRole())
					.addPropertyValue(LABEL_B, association.foreignRole())
					//---
					.addRawLink("dtDefinitionA", association.primaryDtDefinitionName())
					.addRawLink("dtDefinitionB", association.foreignDtDefinitionName())
					//---
					.addPropertyValue(FK_FIELD_NAME, association.fkFieldName())
					.build();

			if (!rawRepository.contains(associationDefinition.getKey())) {
				//Les associations peuvent être déclarées sur les deux noeuds de l'association.
				rawRepository.addRaw(associationDefinition);
			}
		} else if (annotation instanceof io.vertigo.datamodel.data.stereotype.AssociationNN) {
			final io.vertigo.datamodel.data.stereotype.AssociationNN association = (io.vertigo.datamodel.data.stereotype.AssociationNN) annotation;
			//============================================================

			//Attention pamc inverse dans oom les déclarations des objets !!
			final DslRaw associationDefinition = DslRaw.builder(association.name(), DomainGrammar.ASSOCIATION_NN_ENTITY)
					.withPackageName(packageName)
					.addPropertyValue(TABLE_NAME, association.tableName())

					// associationDefinition.
					//On recherche les attributs (>DtField) de cet classe(>Dt_DEFINITION)

					// navigabilités
					.addPropertyValue(NAVIGABILITY_A, association.navigabilityA())
					.addPropertyValue(NAVIGABILITY_B, association.navigabilityB())

					.addPropertyValue(ROLE_A, association.roleA())
					.addPropertyValue(LABEL_A, association.labelA())
					.addPropertyValue(ROLE_B, association.roleB())
					.addPropertyValue(LABEL_B, association.labelB())

					.addRawLink("dtDefinitionA", association.dataDefinitionA())
					.addRawLink("dtDefinitionB", association.dataDefinitionB())
					.build();

			if (!rawRepository.contains(associationDefinition.getKey())) {
				//Les associations peuvent être déclarées sur les deux noeuds de l'association.
				rawRepository.addRaw(associationDefinition);
			}
		}
	}

	private static void parseFieldAnnotations(final Field field, final DslRawBuilder dtDefinition) {
		for (final Annotation annotation : field.getAnnotations()) {
			if (annotation instanceof io.vertigo.datamodel.data.stereotype.Field) {
				//Le nom est automatiquement déduit du nom du champ
				final String fieldName = createFieldName(field);
				parseAnnotation(fieldName, dtDefinition, io.vertigo.datamodel.data.stereotype.Field.class.cast(annotation));
			}
		}
	}

	private static void parseMethodAnnotations(final Method method, final DslRawBuilder dtDefinition) {
		for (final Annotation annotation : method.getAnnotations()) {
			if (annotation instanceof io.vertigo.datamodel.data.stereotype.Field) {
				//Le nom est automatiquement déduit du nom de la méthode
				final String fieldName = createFieldName(method);
				parseAnnotation(fieldName, dtDefinition, io.vertigo.datamodel.data.stereotype.Field.class.cast(annotation));
			}
		}
	}

	/*
	 * Centralisation du parsing des annotations liées à un champ.
	 */
	private static void parseAnnotation(final String fieldName, final DslRawBuilder dtRawBuilder, final io.vertigo.datamodel.data.stereotype.Field field) {
		//Si on trouve un domaine on est dans un objet dynamo.
		final FieldType type = FieldType.valueOf(field.type());

		switch (type) {
			case ID:
				final DslRaw idField = DslRaw.builder(fieldName, DomainGrammar.DT_ID_FIELD_ENTITY)
						.addRawLink("domain", field.smartType())
						.addPropertyValue(LABEL, field.label())
						.build();
				dtRawBuilder.addSubRaw(DomainGrammar.ID_FIELD, idField);
				break;
			case DATA:
				final DslRaw dataField = DslRaw.builder(fieldName, DomainGrammar.DT_DATA_FIELD_ENTITY)
						.addRawLink("domain", field.smartType())
						.addPropertyValue(LABEL, field.label())
						.addPropertyValue(CARDINALITY, field.cardinality().toSymbol())
						.addPropertyValue(PERSISTENT, field.persistent())
						.build();
				dtRawBuilder.addSubRaw(DomainGrammar.DATA_FIELD, dataField);
				break;
			case COMPUTED:
				final DslRaw computedField = DslRaw.builder(fieldName, DomainGrammar.DT_COMPUTED_FIELD_ENTITY)
						.addRawLink("domain", field.smartType())
						.addPropertyValue(LABEL, field.label())
						.addPropertyValue(CARDINALITY, field.cardinality().toSymbol())
						//.addPropertyValue(EXPRESSION, null) no expression on annotation
						.build();
				//Valeurs renseignées automatiquement parce que l'on est dans le cas d'un champ calculé
				dtRawBuilder.addSubRaw(DomainGrammar.COMPUTED_FIELD, computedField);
				break;
			case FOREIGN_KEY:
				//on ne fait rien puisque le champ est défini par une association.
				break;
			default:
				throw new IllegalArgumentException("case " + type + " not implemented");
		}
	}

	/**
	 * Génération du nom du champ (Sous forme de constante) à partir du nom du champ.
	 * @param field champ
	 * @return Constante représentant le nom du champ
	 */
	private static String createFieldName(final Field field) {
		Assertion.check().isNotNull(field);
		//-----
		final String fieldName = field.getName();
		if (StringUtil.isLowerCamelCase(fieldName)) {
			return fieldName;
		}
		throw new IllegalArgumentException(field.getName() + " ne permet pas de donner un nom unique de propriété ");
	}

	/**
	 * Génération du nom du champ (Sous forme de constante) à partir du nom de la méthode.
	 * @param method Method
	 * @return Constante représentant le nom du champ
	 */
	private static String createFieldName(final Method method) {
		Assertion.check().isNotNull(method);
		//-----
		if (method.getName().startsWith("get")) {
			final String propertyName = method.getName().substring("get".length());
			final String fieldName = StringUtil.first2LowerCase(propertyName);
			if (StringUtil.isLowerCamelCase(fieldName)) {
				return fieldName;
			}
		}
		throw new IllegalArgumentException(method.getName() + "ne permet pas de donner un nom unique de propriété ");
	}

	@Override
	public String getType() {
		return "classes";
	}
}

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
package io.vertigo.studio.plugins.source.vertigo.registries.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.core.util.ClassUtil;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.domain.ComputedExpression;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchBuilder;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketchNode;
import io.vertigo.studio.notebook.domain.association.AssociationUtil;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslRaw;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslRawKey;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslSketchFactory;

/**
 * @author pchretien, mlaroche
 */
public final class DomainSketchFactory implements DslSketchFactory {
	private static final Logger LOGGER = LogManager.getLogger(DomainSketchFactory.class);
	//	private static final Gson GSON = new Gson();
	private final Map<SketchKey, DtSketchBuilder> dtDefinitionBuilders = new HashMap<>();

	@Override
	public DslGrammar getGrammar() {
		return new DomainGrammar();
	}

	/** {@inheritDoc} */
	@Override
	public Sketch create(final Notebook notebook, final DslRaw raw) {
		final DslEntity entity = raw.getEntity();
		if (entity.equals(DomainGrammar.DOMAIN_ENTITY)) {
			return createDomain(notebook, raw);
		} else if (entity.equals(DomainGrammar.DT_ENTITY)) {
			return createDtSketch(notebook, raw);
		} else if (entity.equals(DomainGrammar.FRAGMENT_ENTITY)) {
			return createFragmentDtSketch(notebook, raw);
		} else if (entity.equals(DomainGrammar.ASSOCIATION_ENTITY)) {
			return createAssociationSimpleSketch(notebook, raw);
		} else if (entity.equals(DomainGrammar.ASSOCIATION_NN_ENTITY)) {
			return createAssociationNNSketch(notebook, raw);
		}
		throw new IllegalStateException("his kind of raw " + entity + " is not managed by me");
	}

	/*	private List<SketchSupplier> handleDtSketch(final DslSketch dslSketch) {
			final String valuesAsJson = (String) dslSketch.getPropertyValue("values");
			if (valuesAsJson != null) {
				return List.of(
						notebook -> createDtSketch(notebook, dslSketch),
						notebook -> createMasterDataSketch(
								dslSketch.getPackageName() + '.' + SketchUtil.getLocalName(dslSketch.getKey().getName(), DtSketch.PREFIX),
								valuesAsJson));
			}
			return Collections.singletonList(notebook -> createDtSketch(notebook, dslSketch));
	
		}
	
	private static StaticMasterDataSketch createMasterDataSketch(final String className, final String valuesAsJson) {
		final Map<String, MasterDataValue> values = GSON.fromJson(valuesAsJson, new TypeToken<Map<String, MasterDataValue>>() {
			//nothing
		}.getType());
		return new StaticMasterDataSketch(className, values);
	}
	*/
	private static DomainSketch createDomain(final Notebook notebook, final DslRaw domainRaw) {
		final DslRawKey domainRawKey = domainRaw.getKey();
		final String type = domainRaw.getRawKeyByFieldName("dataType").getName();
		final Properties properties = extractProperties(domainRaw);
		switch (type) {
			case "DtObject":
				return DomainSketch.of(domainRawKey.getName(), properties, SketchKey.of(properties.getProperty("TYPE")));
			case "ValueObject":
				return DomainSketch.of(domainRawKey.getName(), properties, ClassUtil.classForName(properties.getProperty("TYPE")));
			default:
				final BasicType dataType = BasicType.valueOf(type);
				return DomainSketch.of(domainRawKey.getName(), properties, dataType);
		}
	}

	private static DtSketch createFragmentDtSketch(final Notebook notebook, final DslRaw dslSketch) {
		final DtSketch from = notebook.resolve(dslSketch.getRawKeyByFieldName("from").getName(), DtSketch.class);

		final String sortFieldName = (String) dslSketch.getPropertyValue(KspProperty.SORT_FIELD);
		final String displayFieldName = (String) dslSketch.getPropertyValue(KspProperty.DISPLAY_FIELD);
		final String handleFieldName = (String) dslSketch.getPropertyValue(KspProperty.HANDLE_FIELD);

		//0. clones characteristics
		final DtSketchBuilder dtDefinitionBuilder = DtSketch.builder(dslSketch.getKey().getName())
				.withFragment(from)
				.withPackageName(dslSketch.getPackageName())
				.withDataSpace(from.getDataSpace())
				.withPackageName(from.getPackageName())
				.withSortField(sortFieldName)
				.withDisplayField(displayFieldName)
				.withHandleField(handleFieldName);

		//1. adds aliases
		for (final DslRaw alias : dslSketch.getSubRaws("alias")) {
			final DtSketchField aliasDtField = from.getField(alias.getKey().getName());

			//--- REQUIRED
			final Cardinality overiddenCardinality = Cardinality.fromSymbol((String) alias.getPropertyValue(KspProperty.CARDINALITY));
			final Cardinality cardinality = overiddenCardinality != null ? overiddenCardinality : aliasDtField.getCardinality();

			//--- LABEL
			final String overiddenLabel = (String) alias.getPropertyValue(KspProperty.LABEL);
			final String label = overiddenLabel != null ? overiddenLabel : aliasDtField.getLabel().getDisplay();

			dtDefinitionBuilder.addDataField(
					aliasDtField.getName(),
					label,
					aliasDtField.getDomain(),
					cardinality,
					aliasDtField.isPersistent());
		}

		//2. adds data and computed fields
		//Déclaration des champs du DT
		final List<DslRaw> fields = dslSketch.getSubRaws(DomainGrammar.DATA_FIELD);
		populateDataDtField(notebook, dtDefinitionBuilder, fields);

		//Déclaration des champs calculés
		final List<DslRaw> computedFields = dslSketch.getSubRaws(DomainGrammar.COMPUTED_FIELD);
		populateComputedDtField(notebook, dtDefinitionBuilder, computedFields);

		final DtSketch dtDefinition = dtDefinitionBuilder
				.build();

		//0. adds ID field -->>> Should be first, but needs an already build DtDefinition
		from.getIdField()
				.ifPresent(idField -> dtDefinitionBuilder.addForeignKey(
						idField.getName(),
						idField.getLabel().getDisplay(),
						idField.getDomain(),
						Cardinality.ONE,
						from.getKey().getName()));

		return dtDefinition;

	}

	/**
	 * @param dtDslSketch Définition de DT
	 */
	private DtSketch createDtSketch(final Notebook notebook, final DslRaw dtDslSketch) {
		//Déclaration de la définition
		final String sortFieldName = (String) dtDslSketch.getPropertyValue(KspProperty.SORT_FIELD);
		final String displayFieldName = (String) dtDslSketch.getPropertyValue(KspProperty.DISPLAY_FIELD);
		final String handleFieldName = (String) dtDslSketch.getPropertyValue(KspProperty.HANDLE_FIELD);
		//-----
		final String tmpStereotype = (String) dtDslSketch.getPropertyValue(KspProperty.STEREOTYPE);
		//Si Stereotype est non renseigné on suppose que la définition est DtStereotype.Data.
		final StudioStereotype stereotype = tmpStereotype != null ? StudioStereotype.valueOf(tmpStereotype) : null;
		//-----
		final String dataSpace = (String) dtDslSketch.getPropertyValue(KspProperty.DATA_SPACE);
		//-----
		final String fragmentOf = (String) dtDslSketch.getPropertyValue(KspProperty.FRAGMENT_OF);
		//-----
		//-----
		final DslRawKey dtSketchKey = dtDslSketch.getKey();
		final DtSketchBuilder dtDefinitionBuilder = DtSketch.builder(dtSketchKey.getName())
				.withPackageName(dtDslSketch.getPackageName())
				.withDataSpace(dataSpace)
				.withSortField(sortFieldName)
				.withDisplayField(displayFieldName)
				.withHandleField(handleFieldName);

		if (stereotype != null) {
			dtDefinitionBuilder.withStereoType(stereotype);
		}
		if (!StringUtil.isBlank(fragmentOf)) {
			dtDefinitionBuilder.withFragment(notebook.resolve(fragmentOf, DtSketch.class));
		}

		//On enregistre les Builder pour pouvoir les mettre à jour sur les associations.
		Assertion.check().isFalse(dtDefinitionBuilders.containsKey(dtSketchKey), "Definition '{0}' already registered", dtSketchKey);
		dtDefinitionBuilders.put(SketchKey.of(dtSketchKey.getName()), dtDefinitionBuilder);

		//Déclaration de la clé primaire
		final List<DslRaw> keys = dtDslSketch.getSubRaws(DomainGrammar.ID_FIELD);
		populateIdDtField(notebook, dtDefinitionBuilder, keys);

		//Déclaration des champs du DT
		final List<DslRaw> fields = dtDslSketch.getSubRaws(DomainGrammar.DATA_FIELD);
		populateDataDtField(notebook, dtDefinitionBuilder, fields);

		//Déclaration des champs calculés
		final List<DslRaw> computedFields = dtDslSketch.getSubRaws(DomainGrammar.COMPUTED_FIELD);
		populateComputedDtField(notebook, dtDefinitionBuilder, computedFields);

		return dtDefinitionBuilder.build();
	}

	/**
	 * Ajoute une liste de champs d'un certain type à la dtDefinition
	 *
	 * @param fields List
	 */
	private static void populateIdDtField(
			final Notebook notebook,
			final DtSketchBuilder dtDefinitionBuilder,
			final List<DslRaw> fields) {

		for (final DslRaw field : fields) {
			final DomainSketch domainSketch = notebook.resolve(field.getRawKeyByFieldName("domain").getName(), DomainSketch.class);
			//--
			Assertion.check().isTrue(field.getPropertyNames().contains(KspProperty.LABEL), "Label est une propriété obligatoire");
			final String label = (String) field.getPropertyValue(KspProperty.LABEL);
			//--
			final String fieldName = field.getKey().getName();
			//-----
			dtDefinitionBuilder.addIdField(fieldName, label, domainSketch);
		}
	}

	/**
	 * Ajoute une liste de champs d'un certain type à la dtDefinition
	 *
	 * @param fields List
	 */
	private static void populateDataDtField(
			final Notebook notebook,
			final DtSketchBuilder dtDefinitionBuilder,
			final List<DslRaw> fields) {

		for (final DslRaw field : fields) {
			final DomainSketch domainSketch = notebook.resolve(field.getRawKeyByFieldName("domain").getName(), DomainSketch.class);
			//--
			Assertion.check().isTrue(field.getPropertyNames().contains(KspProperty.LABEL), "Label est une propriété obligatoire");
			final String label = (String) field.getPropertyValue(KspProperty.LABEL);
			//--
			final String sCardinality = (String) field.getPropertyValue(KspProperty.CARDINALITY);
			final Cardinality cardinality = sCardinality == null ? Cardinality.OPTIONAL_OR_NULLABLE : Cardinality.fromSymbol(sCardinality);
			//--
			final Boolean tmpPersistent = (Boolean) field.getPropertyValue(KspProperty.PERSISTENT);
			//Si PERSISTENT est non renseigné on suppose que le champ est à priori persistant .
			final boolean persistent = tmpPersistent == null || tmpPersistent;
			//--
			final String fieldName = field.getKey().getName();
			//-----
			dtDefinitionBuilder.addDataField(fieldName, label, domainSketch, cardinality, persistent);
		}
	}

	/**
	 * Ajoute une liste de champs d'un certain type à la dtDefinition
	 *
	 * @param fields List
	 */
	private static void populateComputedDtField(
			final Notebook notebook,
			final DtSketchBuilder dtDefinitionBuilder,
			final List<DslRaw> fields) {

		for (final DslRaw field : fields) {
			final DomainSketch domainSketch = notebook.resolve(field.getRawKeyByFieldName("domain").getName(), DomainSketch.class);
			//--
			Assertion.check().isTrue(field.getPropertyNames().contains(KspProperty.LABEL), "Label est une propriété obligatoire");
			final String label = (String) field.getPropertyValue(KspProperty.LABEL);
			//--
			final String sCardinality = (String) field.getPropertyValue(KspProperty.CARDINALITY);
			final Cardinality cardinality = sCardinality == null ? Cardinality.OPTIONAL_OR_NULLABLE : Cardinality.fromSymbol(sCardinality);
			//--
			final String expression = (String) field.getPropertyValue(KspProperty.EXPRESSION);
			final ComputedExpression computedExpression = new ComputedExpression(expression);
			//--
			final DslRawKey fieldKey = field.getKey();

			dtDefinitionBuilder.addComputedField(fieldKey.getName(), label, domainSketch, cardinality, computedExpression);
		}
	}

	private static AssociationNNSketch createAssociationNNSketch(final Notebook notebook, final DslRaw xassociation) {
		final String tableName = (String) xassociation.getPropertyValue(KspProperty.TABLE_NAME);

		final DtSketch dtSketchA = notebook.resolve(xassociation.getRawKeyByFieldName("dtDefinitionA").getName(), DtSketch.class);
		final boolean navigabilityA = (Boolean) xassociation.getPropertyValue(KspProperty.NAVIGABILITY_A);
		final String roleA = (String) xassociation.getPropertyValue(KspProperty.ROLE_A);
		final String labelA = (String) xassociation.getPropertyValue(KspProperty.LABEL_A);

		final DtSketch dtSketchB = notebook.resolve(xassociation.getRawKeyByFieldName("dtDefinitionB").getName(), DtSketch.class);
		final boolean navigabilityB = (Boolean) xassociation.getPropertyValue(KspProperty.NAVIGABILITY_B);
		final String roleB = (String) xassociation.getPropertyValue(KspProperty.ROLE_B);
		final String labelB = (String) xassociation.getPropertyValue(KspProperty.LABEL_B);

		final AssociationSketchNode associationNodeA = new AssociationSketchNode(dtSketchA, navigabilityA, roleA, labelA, true, false);
		final AssociationSketchNode associationNodeB = new AssociationSketchNode(dtSketchB, navigabilityB, roleB, labelB, true, false);
		return new AssociationNNSketch(xassociation.getKey().getName(), tableName, associationNodeA, associationNodeB);
	}

	private AssociationSimpleSketch createAssociationSimpleSketch(final Notebook notebook, final DslRaw xassociation) {

		final String associationType = (String) xassociation.getPropertyValue("type");

		final String multiplicityA;
		final String multiplicityB;
		final Boolean navigabilityA;
		final Boolean navigabilityB;
		if (associationType != null) {
			switch (associationType) {
				case "*>1":
					multiplicityA = "0..*";
					multiplicityB = "1..1";
					navigabilityA = false;
					navigabilityB = true;
					break;
				case "*>?":
					multiplicityA = "0..*";
					multiplicityB = "0..1";
					navigabilityA = false;
					navigabilityB = true;
					break;
				case "*>*":
					multiplicityA = "0..*";
					multiplicityB = "0..*";
					navigabilityA = false;
					navigabilityB = true;
					break;
				default:
					throw new VSystemException("type of asssociation not supported : '{0}', available types are : '{1}'", associationType, " *>1 , *>? , *>* ");
			}
		} else {
			multiplicityA = (String) xassociation.getPropertyValue(KspProperty.MULTIPLICITY_A);
			navigabilityA = (Boolean) xassociation.getPropertyValue(KspProperty.NAVIGABILITY_A);
			multiplicityB = (String) xassociation.getPropertyValue(KspProperty.MULTIPLICITY_B);
			navigabilityB = (Boolean) xassociation.getPropertyValue(KspProperty.NAVIGABILITY_B);
			//---
			Assertion.check()
					.isNotNull(multiplicityA)
					.isNotNull(navigabilityA)
					.isNotNull(multiplicityB)
					.isNotNull(navigabilityB);
		}
		// Vérification que l'on est bien dans le cas d'une association simple de type 1-n
		if (AssociationUtil.isMultiple(multiplicityB) && AssociationUtil.isMultiple(multiplicityA)) {
			//Relation n-n
			throw new IllegalArgumentException("Utiliser la déclaration AssociationNN");
		}
		if (!AssociationUtil.isMultiple(multiplicityB) && !AssociationUtil.isMultiple(multiplicityA)) {
			//Relation 1-1
			throw new IllegalArgumentException("Les associations 1-1 sont interdites");
		}

		final String fkFieldName = (String) xassociation.getPropertyValue(KspProperty.FK_FIELD_NAME);

		final DtSketch dtDefinitionA = notebook.resolve(xassociation.getRawKeyByFieldName("dtDefinitionA").getName(), DtSketch.class);
		final String roleAOpt = (String) xassociation.getPropertyValue(KspProperty.ROLE_A);
		final String roleA = roleAOpt != null ? roleAOpt : dtDefinitionA.getLocalName();
		final String labelAOpt = (String) xassociation.getPropertyValue(KspProperty.LABEL_A);
		final String labelA = labelAOpt != null ? labelAOpt : dtDefinitionA.getLocalName();

		final DtSketch dtDefinitionB = notebook.resolve(xassociation.getRawKeyByFieldName("dtDefinitionB").getName(), DtSketch.class);
		final String roleBOpt = (String) xassociation.getPropertyValue(KspProperty.ROLE_B);
		final String roleB = roleBOpt != null ? roleBOpt : dtDefinitionB.getLocalName();
		final String labelB = (String) xassociation.getPropertyValue(KspProperty.LABEL_B);

		final AssociationSketchNode associationNodeA = new AssociationSketchNode(dtDefinitionA, navigabilityA, roleA, labelA, AssociationUtil.isMultiple(multiplicityA), AssociationUtil.isNotNull(multiplicityA));
		final AssociationSketchNode associationNodeB = new AssociationSketchNode(dtDefinitionB, navigabilityB, roleB, labelB, AssociationUtil.isMultiple(multiplicityB), AssociationUtil.isNotNull(multiplicityB));

		final AssociationSimpleSketch associationSimpleDefinition = new AssociationSimpleSketch(xassociation.getKey().getName(), fkFieldName, associationNodeA, associationNodeB);

		final AssociationSketchNode primaryAssociationNode = associationSimpleDefinition.getPrimaryAssociationNode();
		final AssociationSketchNode foreignAssociationNode = associationSimpleDefinition.getForeignAssociationNode();

		final DtSketch fkDefinition = primaryAssociationNode.getDtSketch();

		LOGGER.trace("{} : ajout d'une FK [{}] sur la table '{}'", xassociation.getKey(), fkFieldName, foreignAssociationNode.getDtSketch());

		final String label = primaryAssociationNode.getLabel();
		final Cardinality fieldCardinality = primaryAssociationNode.isNotNull() ? Cardinality.ONE : Cardinality.OPTIONAL_OR_NULLABLE;
		dtDefinitionBuilders.get(foreignAssociationNode.getDtSketch().getKey())
				.addForeignKey(fkFieldName, label, fkDefinition.getIdField().get().getDomain(), fieldCardinality, fkDefinition.getKey().getName());
		//On estime qu'une FK n'est ni une colonne de tri ni un champ d'affichage

		return associationSimpleDefinition;
	}

	/**
	 * Extrait le PropertyContainer<DtProperty> d'une DynamicDefinition.
	 * Associe les DtProperty et les KspProperty par leur nom.
	 * @param dslDefinition Definition
	 * @return Container des propriétés
	 */
	private static Properties extractProperties(final DslRaw dslDefinition) {
		final Properties properties = new Properties();

		//On associe les propriétés Dt et Ksp par leur nom.
		for (final String entityPropertyName : dslDefinition.getPropertyNames()) {
			properties.put(entityPropertyName, dslDefinition.getPropertyValue(entityPropertyName));
		}
		return properties;
	}

	/** {@inheritDoc} */
	@Override
	public List<DslRaw> onNewRaw(final DslRaw dslDefinition) {
		if (DomainGrammar.DT_ENTITY.equals(dslDefinition.getEntity())
				|| DomainGrammar.FRAGMENT_ENTITY.equals(dslDefinition.getEntity())) {
			//Dans le cas des DT on ajoute le domain
			return Collections.singletonList(createDtDomain(dslDefinition.getKey().getName(), dslDefinition.getPackageName()));
		}
		return Collections.emptyList();
	}

	/*
	 * Construction du domaine relatif à un DT : DoDtXxxXX
	 */
	private static DslRaw createDtDomain(final String definitionName, final String packageName) {
		//C'est le constructeur de DtDomainStandard qui vérifie la cohérence des données passées.
		//Notamment la validité de la liste des contraintes et la nullité du formatter

		final DslEntity metaDefinitionDomain = DomainGrammar.DOMAIN_ENTITY;

		return DslRaw.builder(DomainSketch.PREFIX + definitionName, metaDefinitionDomain)
				.withPackageName(packageName)
				.addRawLink("dataType", "DtObject")
				//On dit que le domaine possède une prop définissant le type comme étant le nom du DT
				.addPropertyValue(KspProperty.TYPE, definitionName)
				.build();
	}

}

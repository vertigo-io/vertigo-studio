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
package io.vertigo.studio.plugins.source.vertigo.loaders.xml;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslRawBuilder;
import io.vertigo.studio.impl.source.dsl.raw.DslRawKey;
import io.vertigo.studio.impl.source.dsl.raw.DslRawRepository;
import io.vertigo.studio.notebook.domain.association.AssociationUtil;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;
import io.vertigo.studio.plugins.source.vertigo.factories.domain.DomainGrammar;
import io.vertigo.studio.plugins.source.vertigo.loaders.Loader;

/**
 * Parser d'un fichier powerAMC/OOM ou EA/XMI.
 *
 * @author pchretien, mlaroche
 */
public abstract class AbstractXmlLoader implements Loader {

	private static final int MAX_COLUMN_LENGTH = 30;
	private static final Logger LOGGER = LogManager.getLogger(AbstractXmlLoader.class);

	private static final String DT_DEFINITION_PREFIX = "Dt";
	private final ResourceManager resourceManager;
	private final boolean constFieldNameInSource;

	/**
	 * Constructor.
	 * @param constFieldNameInSource FieldName in file is in CONST_CASE instead of camelCase
	 * @param resourceManager the vertigo resourceManager
	 */
	public AbstractXmlLoader(final boolean constFieldNameInSource, final ResourceManager resourceManager) {
		Assertion.check().isNotNull(resourceManager);
		//-----
		this.resourceManager = resourceManager;
		this.constFieldNameInSource = constFieldNameInSource;
	}

	/** {@inheritDoc} */
	@Override
	public final void load(final String resourcePath, final DslRawRepository rawRepository) {
		final URL xmiFileURL = resourceManager.resolve(resourcePath);

		try {
			final SAXParserFactory factory = SAXParserFactory.newInstance();
			io.vertigo.core.util.XmlUtil.secureXmlXXEByOwasp(factory);

			final SAXParser saxParser = factory.newSAXParser();
			try (final InputStream is = xmiFileURL.openStream()) {
				saxParser.parse(is, getHandler());
			}
		} catch (final Exception e) {
			throw WrappedException.wrap(e, "erreur lors de la lecture du fichier xmi : {0}", xmiFileURL);
		}
		Assertion.check()
				.isNotBlank(resourcePath)
				.isNotNull(rawRepository);
		//-----

		for (final XmlClass clazz : getClasses()) {
			rawRepository.addRaw(toRaw(clazz));
		}

		for (final XmlAssociation association : getAssociations()) {
			rawRepository.addRaw(toRaw(association, rawRepository));
		}
	}

	protected abstract DefaultHandler getHandler();

	/**
	 * Récupération des classes déclarées.
	 * @return Liste des classes
	 */
	protected abstract List<XmlClass> getClasses();

	/**
	 * Récupération des associations déclarées dans l'OOM.
	 * @return Liste des associations
	 */
	protected abstract List<XmlAssociation> getAssociations();

	protected final boolean isConstFieldNameInSource() {
		return constFieldNameInSource;
	}

	private static DslRaw toRaw(final XmlClass clazz) {
		final DslEntity dtEntity = DomainGrammar.DT_ENTITY;
		final DslRawBuilder dtRawBuilder = DslRaw.builder(getDtRawKey(clazz.getCode()), dtEntity)
				.withPackageName(clazz.getPackageName())
				//Par défaut les DT lues depuis le OOM/XMI sont persistantes.
				.addPropertyValue(KspProperty.STEREOTYPE, clazz.getStereotype());

		for (final XmlAttribute attribute : clazz.getKeyAttributes()) {
			final DslRaw dtField = toDynamicDefinition(attribute);
			dtRawBuilder.addSubRaw(DomainGrammar.ID_FIELD, dtField);
		}
		for (final XmlAttribute tagAttribute : clazz.getFieldAttributes()) {
			final DslRaw dtField = toDynamicDefinition(tagAttribute);
			dtRawBuilder.addSubRaw(DomainGrammar.DATA_FIELD, dtField);
		}
		return dtRawBuilder.build();
	}

	private static DslRaw toDynamicDefinition(final XmlAttribute attribute) {
		final DslEntity dtFieldEntity = DomainGrammar.DT_DATA_FIELD_ENTITY;
		final Cardinality cardinality = attribute.isNotNull() ? Cardinality.ONE : Cardinality.OPTIONAL_OR_NULLABLE;
		return DslRaw.builder(attribute.getCode(), dtFieldEntity)
				.addPropertyValue(KspProperty.LABEL, attribute.getLabel())
				.addPropertyValue(KspProperty.PERSISTENT, attribute.isPersistent())
				.addPropertyValue(KspProperty.CARDINALITY, cardinality.toSymbol())
				.addRawLink("domain", attribute.getDomain())
				.build();
	}

	private static DslRaw toRaw(final XmlAssociation association, final DslRawRepository rawRepository) {
		final DslEntity associationEntity = DomainGrammar.ASSOCIATION_ENTITY;
		final DslEntity associationNNEntity = DomainGrammar.ASSOCIATION_NN_ENTITY;

		//On regarde si on est dans le cas d'une association simple ou multiple
		final boolean isAssociationNN = AssociationUtil.isMultiple(association.getMultiplicityA()) && AssociationUtil.isMultiple(association.getMultiplicityB());
		final DslEntity entity;
		if (isAssociationNN) {
			entity = associationNNEntity;
		} else {
			entity = associationEntity;
		}

		final String associationDefinitionName = (isAssociationNN ? "Ann" : "A") + association.getCode();

		//On crée l'association
		final DslRawBuilder associationRawBuilder = DslRaw.builder(associationDefinitionName, entity)
				.withPackageName(association.getPackageName())
				.addPropertyValue(KspProperty.NAVIGABILITY_A, association.isNavigableA())
				.addPropertyValue(KspProperty.NAVIGABILITY_B, association.isNavigableB())
				//---
				.addPropertyValue(KspProperty.LABEL_A, association.getRoleLabelA())
				//On transforme en CODE ce qui est écrit en toutes lettres.
				.addPropertyValue(KspProperty.ROLE_A, XmlUtil.french2Java(association.getRoleLabelA()))
				.addPropertyValue(KspProperty.LABEL_B, association.getRoleLabelB())
				.addPropertyValue(KspProperty.ROLE_B, XmlUtil.french2Java(association.getRoleLabelB()))
				//---
				.addRawLink("dtDefinitionA", getDtRawKey(association.getCodeA()))
				.addRawLink("dtDefinitionB", getDtRawKey(association.getCodeB()));

		if (isAssociationNN) {
			//Dans le cas d'une association NN il faut établir le nom de la table intermédiaire qui porte les relations
			final String tableName = StringUtil.camelToConstCase(association.getCode()); // Need a constCase for tableName
			associationRawBuilder.addPropertyValue(KspProperty.TABLE_NAME, tableName);
			LOGGER.trace("isAssociationNN:Code= {}", association.getCode());
		} else {
			LOGGER.trace("!isAssociationNN:Code= {}", association.getCode());
			//Dans le cas d'une NN ses deux propriétés sont redondantes ;
			//elles ne font donc pas partie de la définition d'une association de type NN
			associationRawBuilder
					.addPropertyValue(KspProperty.MULTIPLICITY_A, association.getMultiplicityA())
					.addPropertyValue(KspProperty.MULTIPLICITY_B, association.getMultiplicityB())
					.addPropertyValue(KspProperty.FK_FIELD_NAME, buildFkFieldName(association, rawRepository));

		}
		return associationRawBuilder.build();
	}

	private static String buildFkFieldName(final XmlAssociation association, final DslRawRepository rawRepository) {
		// Dans le cas d'une association simple, on recherche le nom de la FK
		// recherche de code de contrainte destiné à renommer la fk selon convention du vbsript PowerAMC
		// Cas de la relation 1-n : où le nom de la FK est redéfini.
		// Exemple : DOS_UTI_LIQUIDATION (relation entre dossier et utilisateur : FK >> UTILISATEUR_ID_LIQUIDATION)
		final DslRaw dtRawA = rawRepository.getRaw(getDtRawKey(association.getCodeA()));
		final DslRaw dtRawB = rawRepository.getRaw(getDtRawKey(association.getCodeB()));

		final DslRaw foreignKeyRaw = AssociationUtil.isAPrimaryNode(association.getMultiplicityA(), association.getMultiplicityB()) ? dtRawA : dtRawB;
		final List<DslRaw> primaryRawKeys = foreignKeyRaw.getSubRaws(DomainGrammar.ID_FIELD);
		if (primaryRawKeys.isEmpty()) {
			throw new IllegalArgumentException("Pour l'association '" + association.getCode() + "' aucune clé primaire sur la définition '" + foreignKeyRaw.getKey() + "'");
		}
		if (primaryRawKeys.size() > 1) {
			throw new IllegalArgumentException("Pour l'association '" + association.getCode() + "' clé multiple non géré sur '" + foreignKeyRaw.getKey() + "'");
		}
		if (dtRawA.getKey().equals(dtRawB.getKey()) && association.getCodeName() == null) {
			throw new IllegalArgumentException("Pour l'association '" + association.getCode() + "' le nom de la clé est obligatoire (AutoJointure) '"
					+ foreignKeyRaw.getKey()
					+ "'. Ce nom est déduit du code l'association, le code doit être composé ainsi : {Trigramme Table1}{Trigramme Table2}{Code association}."
					+ " Par exemple : DosUtiEmmeteur, DosUtiDestinataire, DosDosParent, ...");
		}

		//On récupère le nom de LA clé primaire .
		final DslRawKey pkField = primaryRawKeys.get(0).getKey();

		//Par défaut le nom de la clé étrangère est constituée de la clé primaire référencée.
		String fkFieldName = pkField.getName();

		//Si l'association possède une nom défini par l'utilisateur, alors on l'ajoute à la FK avec un séparateur.
		if (association.getCodeName() != null) {
			//On construit le nom de la clé étrangère.
			fkFieldName = fkFieldName + StringUtil.first2UpperCase(association.getCodeName());
		}

		//On raccourci le nom de la clé étrangère.
		if (fkFieldName.length() > MAX_COLUMN_LENGTH) { // 30 est le max de dynamo (et de Oracle)
			fkFieldName = fkFieldName.substring(0, MAX_COLUMN_LENGTH);
			while (fkFieldName.endsWith("_")) {
				fkFieldName = fkFieldName.substring(0, fkFieldName.length() - 1);
			}

		}
		LOGGER.trace(KspProperty.FK_FIELD_NAME + "= {}", fkFieldName);
		//-----
		Assertion.check().isNotNull(fkFieldName, "La clé primaire n''a pas pu être définie pour l'association '{0}'", association.getCode());
		return fkFieldName;
	}

	private static DslRawKey getDtRawKey(final String code) {
		return DslRawKey.of(DT_DEFINITION_PREFIX + code);
	}

}

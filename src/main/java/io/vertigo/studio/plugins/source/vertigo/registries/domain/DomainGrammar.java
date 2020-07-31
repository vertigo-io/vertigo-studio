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

import static io.vertigo.studio.plugins.source.vertigo.KspProperty.CARDINALITY;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.DATA_SPACE;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.DISPLAY_FIELD;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.EXPRESSION;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.FK_FIELD_NAME;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.FRAGMENT_OF;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.HANDLE_FIELD;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.INDEX_TYPE;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.LABEL;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.LABEL_A;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.LABEL_B;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.MAX_LENGTH;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.MULTIPLICITY_A;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.MULTIPLICITY_B;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.NAVIGABILITY_A;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.NAVIGABILITY_B;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.PERSISTENT;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.ROLE_A;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.ROLE_B;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.SORT_FIELD;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.STEREOTYPE;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.STORE_TYPE;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.TABLE_NAME;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.TYPE;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.UNIT;
import static io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslPropertyType.Boolean;
import static io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslPropertyType.Integer;
import static io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslPropertyType.String;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.BasicType;
import io.vertigo.core.util.ListBuilder;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslDefinition;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;

/**
 * @author pchretien, mlaroche
 */
public final class DomainGrammar implements DslGrammar {
	/**
	 * Clé des FIELD_DEFINITION de type PK utilisés dans les DT_DEFINITION.
	 */
	public static final String ID_FIELD = "id";
	/**
	 * Clé des FIELD_DEFINITION de type FIELD utilisés dans les DT_DEFINITION.
	 */
	public static final String DATA_FIELD = "field";
	/**
	 * Clé des FIELD_DEFINITION de type COMPUTED utilisés dans les DT_DEFINITION.
	 */
	public static final String COMPUTED_FIELD = "computed";

	/**Définition d'un domain.*/
	public static final DslEntity DOMAIN_ENTITY;

	/**the data types are provided by the language (String, Integer...) */
	private static final DslEntity DATA_TYPE_ENTITY = DslEntity.builder("DataType")
			.withProvided()
			.build();

	/**Fields*/
	public static final DslEntity DT_ID_FIELD_ENTITY;
	public static final DslEntity DT_DATA_FIELD_ENTITY;
	public static final DslEntity DT_COMPUTED_FIELD_ENTITY;

	public static final DslEntity DT_DEFINITION_ENTITY;
	/**Définition d'une association simple.*/
	public static final DslEntity ASSOCIATION_ENTITY;
	/**Définition d'une association NN.*/
	public static final DslEntity ASSOCIATION_NN_ENTITY;

	/**
	 * Fragments
	 */
	public static final DslEntity FRAGMENT_ENTITY;

	static {
		DOMAIN_ENTITY = DslEntity.builder("Domain")
				.addOptionalField(MAX_LENGTH, Integer)
				.addOptionalField(TYPE, String)
				.addOptionalField(UNIT, String)
				.addOptionalField(INDEX_TYPE, String)
				.addOptionalField(STORE_TYPE, String)
				.addRequiredField("dataType", DATA_TYPE_ENTITY.getLink())
				.build();

		DT_ID_FIELD_ENTITY = DslEntity.builder("IdField")
				.addRequiredField(LABEL, String)
				.addRequiredField("domain", DOMAIN_ENTITY.getLink())
				.build();

		DT_DATA_FIELD_ENTITY = DslEntity.builder("DataField")
				.addRequiredField(LABEL, String)
				.addRequiredField(CARDINALITY, String)
				.addRequiredField("domain", DOMAIN_ENTITY.getLink())
				.addOptionalField(PERSISTENT, Boolean)
				.build();

		DT_COMPUTED_FIELD_ENTITY = DslEntity.builder("ComputedField")
				.addRequiredField(LABEL, String)
				.addRequiredField(CARDINALITY, String)
				.addRequiredField("domain", DOMAIN_ENTITY.getLink())
				.addOptionalField(EXPRESSION, String)
				.build();

		DT_DEFINITION_ENTITY = DslEntity.builder("DtDefinition")
				.addOptionalField(DISPLAY_FIELD, String)
				.addOptionalField(SORT_FIELD, String)
				.addOptionalField(HANDLE_FIELD, String)
				.addManyFields(DATA_FIELD, DT_DATA_FIELD_ENTITY)
				.addManyFields(COMPUTED_FIELD, DT_COMPUTED_FIELD_ENTITY)
				.addOptionalField(ID_FIELD, DT_ID_FIELD_ENTITY)
				.addOptionalField(FRAGMENT_OF, String)
				.addOptionalField(STEREOTYPE, String)
				.addOptionalField(DATA_SPACE, String)
				.build();

		final DslEntity fieldAliasEntity = DslEntity.builder("fieldAlias")
				.addOptionalField(LABEL, String)
				.addOptionalField(CARDINALITY, Boolean)
				.build();

		FRAGMENT_ENTITY = DslEntity.builder("Fragment")
				.addRequiredField("from", DT_DEFINITION_ENTITY.getLink())
				.addManyFields("alias", fieldAliasEntity) //on peut ajouter des champs
				.addOptionalField(DISPLAY_FIELD, String)
				.addOptionalField(SORT_FIELD, String)
				.addOptionalField(HANDLE_FIELD, String)
				.addManyFields(DATA_FIELD, DT_DATA_FIELD_ENTITY) //on peut ajouter des champs
				.addManyFields(COMPUTED_FIELD, DT_COMPUTED_FIELD_ENTITY) //et des computed
				.build();

		ASSOCIATION_ENTITY = DslEntity.builder("Association")
				.addOptionalField(FK_FIELD_NAME, String)
				.addOptionalField(MULTIPLICITY_A, String)
				.addOptionalField(NAVIGABILITY_A, Boolean)
				.addOptionalField(ROLE_A, String)
				.addOptionalField(LABEL_A, String)
				.addOptionalField(MULTIPLICITY_B, String)
				.addOptionalField(NAVIGABILITY_B, Boolean)
				.addOptionalField(ROLE_B, String)
				.addRequiredField(LABEL_B, String)
				.addRequiredField("dtDefinitionA", DT_DEFINITION_ENTITY.getLink())
				.addRequiredField("dtDefinitionB", DT_DEFINITION_ENTITY.getLink())
				.addOptionalField("type", String)
				.build();

		ASSOCIATION_NN_ENTITY = DslEntity.builder("AssociationNN")
				.addRequiredField(TABLE_NAME, String)
				.addRequiredField(NAVIGABILITY_A, Boolean)
				.addRequiredField(ROLE_A, String)
				.addRequiredField(LABEL_A, String)
				.addRequiredField(NAVIGABILITY_B, Boolean)
				.addRequiredField(ROLE_B, String)
				.addRequiredField(LABEL_B, String)
				.addRequiredField("dtDefinitionA", DT_DEFINITION_ENTITY.getLink())
				.addRequiredField("dtDefinitionB", DT_DEFINITION_ENTITY.getLink())
				.build();

	}

	@Override
	public List<DslEntity> getEntities() {
		return List.of(
				DATA_TYPE_ENTITY,
				//---
				DOMAIN_ENTITY,
				FRAGMENT_ENTITY,
				DT_DEFINITION_ENTITY,
				ASSOCIATION_ENTITY,
				ASSOCIATION_NN_ENTITY);
	}

	@Override
	public List<DslDefinition> getRootDefinitions() {
		//We are listing all primitives types
		final List<String> types = new ListBuilder()
				.addAll(Arrays.stream(BasicType.values())
						.map(BasicType::name)
						.collect(Collectors.toList()))
				.add("DtObject")
				.add("ValueObject")
				.build();

		return types
				.stream()
				.map(type -> DslDefinition.builder(type, DATA_TYPE_ENTITY).build())
				.collect(Collectors.toList());
	}
}

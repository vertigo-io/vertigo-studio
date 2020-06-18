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
package io.vertigo.studio.plugins.mda.vertigo.domain.java.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.domain.StudioDtField;
import io.vertigo.studio.metamodel.domain.StudioDtField.FieldType;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationDefinition;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationSimpleDefinition;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;

/**
 * Model used to define a DtField.
 *
 * @author pchretien, mlaroche
 */
public final class StudioDtFieldModel {
	private final StudioDtDefinition dtDefinition;
	private final StudioDtField dtField;
	private final List<? extends StudioAssociationDefinition> associationDefinitions;
	private final String javaType;
	private final String javaTypeLabel;

	/***
	 * Constructeur.
	 * @param dtField Champ à générer
	 */
	StudioDtFieldModel(final StudioDtDefinition dtDefinition, final StudioDtField dtField, final List<? extends StudioAssociationDefinition> associationDefinitions, final Function<String, String> classNameFromDt) {
		Assertion.check()
				.notNull(dtDefinition)
				.notNull(dtField)
				.notNull(associationDefinitions)
				.notNull(classNameFromDt);
		//-----
		this.dtDefinition = dtDefinition;
		this.dtField = dtField;
		this.associationDefinitions = associationDefinitions;
		//---
		javaType = DomainUtil.buildJavaType(dtField, classNameFromDt);
		javaTypeLabel = DomainUtil.buildJavaTypeLabel(dtField, classNameFromDt);
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
	public StudioDtField getSource() {
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
	public String getJavaType() {
		return javaType;
	}

	/**
	 * @return Type java du champ
	 */
	public String getJavaTypeLabel() {
		return javaTypeLabel;
	}

	/**
	 * @return Label du champ
	 */
	public String getDisplay() {
		return dtField.getLabel().getDisplay();
	}

	/**
	 * @return Label du champ
	 */
	public boolean isSortField() {
		return dtDefinition.getSortField().isPresent() && dtDefinition.getSortField().get().getName().equals(dtField.getName());
	}

	/**
	 * @return Label du champ
	 */
	public boolean isDisplayField() {
		return dtDefinition.getDisplayField().isPresent() && dtDefinition.getDisplayField().get().getName().equals(dtField.getName());
	}

	/**
	 * @return Si la propriété est requise
	 */
	public boolean isRequired() {
		return dtField.getCardinality().hasOne();
	}

	/**
	 * @return Code java correspondant à l'expression de ce champ calculé
	 */
	public String getJavaCode() {
		return dtField.getComputedExpression().getJavaCode();
	}

	private boolean isChildOfEntity() {
		return dtDefinition.getFragment().isEmpty();
	}

	public boolean isForeignKey() {
		return isChildOfEntity() && dtField.getType() == FieldType.FOREIGN_KEY;
	}

	public StudioAssociationModel getAssociation() {
		Assertion.check().state(isChildOfEntity(), "an association must be declared on an entity");
		//---
		return associationDefinitions
				.stream()
				.filter(association -> association instanceof StudioAssociationSimpleDefinition)
				.map(association -> (StudioAssociationSimpleDefinition) association)
				.filter(association -> association.getFKField().equals(dtField))
				.map(association -> new StudioAssociationModel(association, association.getPrimaryAssociationNode()))
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
	}

}

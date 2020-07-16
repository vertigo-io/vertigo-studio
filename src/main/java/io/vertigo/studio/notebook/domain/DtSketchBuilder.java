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
package io.vertigo.studio.notebook.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.locale.MessageKey;
import io.vertigo.core.locale.MessageText;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.tools.DefinitionUtil;

/**
 * This class must be used to build a DtDefinition.
 *
 * Each dtDefinition must have a name following this pattern DT_XXX_YYYY
 *
 * @author pchretien, mlaroche
 */
public final class DtSketchBuilder implements Builder<DtSketch> {

	private static class MessageKeyImpl implements MessageKey {
		private static final long serialVersionUID = 6959551752755175151L;

		private final String name;

		MessageKeyImpl(final String name) {
			this.name = name;
		}

		/** {@inheritDoc} */
		@Override
		public String name() {
			return name;
		}
	}

	private DtSketch dtDefinition;
	private final String myName;
	private DtSketch myFragment;
	private String myPackageName;
	private StudioStereotype myStereotype;
	private DtSketchField myIdField;
	private final List<DtSketchField> myFields = new ArrayList<>();
	private String myDataSpace;
	private String mySortFieldName;
	private String myDisplayFieldName;
	private String myHandleFieldName;

	/**
	 * Constructor.
	 * @param name the name of the dtDefinition
	 */
	DtSketchBuilder(final String name) {
		Assertion.check().isNotBlank(name);
		//-----
		myName = name;
	}

	/**
	 * Sets packageName
	 * @param packageName the name of the package (nullable)
	 * @return this builder
	 */
	public DtSketchBuilder withPackageName(final String packageName) {
		//the packageName can be null
		//-----
		myPackageName = packageName;
		return this;
	}

	/**
	 * Sets fragment
	 * @param fragment Persistent root DtDefinition for this fragment
	 * @return this builder
	 */
	public DtSketchBuilder withFragment(final DtSketch fragment) {
		Assertion.check().isNotNull(fragment);
		//---
		myStereotype = StudioStereotype.Fragment;
		myFragment = fragment;
		return this;
	}

	/**
	 * Sets the stereotype of the dtDefinition.
	 *
	 * @param stereotype the stereotype of the dtDefinition
	 * @return this builder
	 */
	public DtSketchBuilder withStereoType(final StudioStereotype stereotype) {
		Assertion.check().isNotNull(stereotype);
		//-----
		myStereotype = stereotype;
		return this;
	}

	/**
	 * Adds a field linked to another dtDefinition (aka foreign key).
	 *
	 * @param fieldName the name of the field
	 * @param fkDtDefinitionName the name of the linked definition
	 * @param label the label of the field
	 * @param domainSketch the domain of the field
	 * @param required if the field is required
	 * @return this builder
	 */
	public DtSketchBuilder addForeignKey(
			final String fieldName,
			final String label,
			final DomainSketch domainSketch,
			final Cardinality cardinality,
			final String fkDtDefinitionName) {
		//Pour l'instant on ne gère pas les chamsp computed dynamiques
		final boolean persistent = true;
		final DtSketchField dtField = createField(
				fieldName,
				DtSketchField.FieldType.FOREIGN_KEY,
				domainSketch,
				label,
				cardinality,
				persistent,
				fkDtDefinitionName,
				null);
		//On suppose que le build est déjà effectué. TODO: WTF
		dtDefinition.registerDtField(dtField);
		return this;
	}

	/**
	 * Adds a computed field.
	 *
	 * @param fieldName the name of the field
	 * @param label the label of the field
	 * @param domainSketch the domain of the field
	 * @param computedExpression the expression use to compute the field
	 * @return this builder
	 */
	public DtSketchBuilder addComputedField(
			final String fieldName,
			final String label,
			final DomainSketch domainSketch,
			final Cardinality cardinality,
			final ComputedExpression computedExpression) {
		final boolean persistent = false;
		final DtSketchField dtField = createField(
				fieldName,
				DtSketchField.FieldType.COMPUTED,
				domainSketch,
				label,
				cardinality,
				persistent,
				null,
				computedExpression);
		myFields.add(dtField);
		return this;
	}

	/**
	 * Adds a common data field.
	 *
	 * @param fieldName the name of the field
	 * @param domainSketch the domain of the field
	 * @param label the label of the field
	 * @param required if the field is required
	 * @param persistent if the fiels is persistent
	 * @return this builder
	 */
	public DtSketchBuilder addDataField(
			final String fieldName,
			final String label,
			final DomainSketch domainSketch,
			final Cardinality cardinality,
			final boolean persistent) {
		//the field is dynamic if and only if the dtDefinition is dynamic
		final DtSketchField dtField = createField(
				fieldName,
				DtSketchField.FieldType.DATA,
				domainSketch,
				label,
				cardinality,
				persistent,
				null,
				null);
		myFields.add(dtField);
		return this;
	}

	/**
	 * Adds an ID field.
	 * This field is required.
	 *
	 * @param fieldName the name of the field
	 * @param domainSketch the domain of the field
	 * @param label the label of the field
	 * @return this builder
	 */
	public DtSketchBuilder addIdField(
			final String fieldName,
			final String label,
			final DomainSketch domainSketch) {
		Assertion.check().isNull(myIdField, "only one ID per Entity is permitted, error on {0}", myPackageName);
		//---
		//le champ ID est tjrs required
		final Cardinality cardinality = Cardinality.ONE;
		//le champ ID est persistant SSI la définition est persitante.
		final boolean persistent = true;
		//le champ  est dynamic SSI la définition est dynamique
		final DtSketchField dtField = createField(
				fieldName,
				DtSketchField.FieldType.ID,
				domainSketch,
				label,
				cardinality,
				persistent,
				null,
				null);
		myIdField = dtField;
		myFields.add(dtField);
		return this;
	}

	private DtSketchField createField(
			final String fieldName,
			final DtSketchField.FieldType type,
			final DomainSketch domainSketch,
			final String strLabel,
			final Cardinality cardinality,
			final boolean persistent,
			final String fkDtDefinitionName,
			final ComputedExpression computedExpression) {

		final String shortName = DefinitionUtil.getLocalName(myName, DtSketch.PREFIX);
		//-----
		// Le DtField vérifie ses propres règles et gère ses propres optimisations
		final String id = "fld" + shortName + '$' + fieldName;

		//2. Sinon Indication de longueur portée par le champ du DT.
		//-----
		final MessageText labelMsg = MessageText.ofDefaultMsg(strLabel, new MessageKeyImpl(id));
		// Champ CODE_COMMUNE >> getCodeCommune()
		//Un champ est persisanty s'il est marqué comme tel et si la définition l'est aussi.
		return new DtSketchField(
				id,
				fieldName,
				type,
				domainSketch,
				labelMsg,
				cardinality,
				persistent,
				fkDtDefinitionName,
				computedExpression);
	}

	/**
	 * Sets the dataSpace to which the dtDefinition belongs.
	 * @param dataSpace the dataSpace to which the DtDefinition is mapped.
	 * @return this builder
	 */
	public DtSketchBuilder withDataSpace(final String dataSpace) {
		//the dataSpace can be null, in this case the default dataSpace will be chosen.
		//-----
		myDataSpace = dataSpace;
		return this;
	}

	/**
	 * Specifies which field to be used for sorting
	 * @param sortFieldName fieldName to use
	 * @return this builder
	 */
	public DtSketchBuilder withSortField(final String sortFieldName) {
		mySortFieldName = sortFieldName;
		return this;
	}

	/**
	 * Specifies which field to be used for display
	 * @param displayFieldName fieldName to use
	 * @return this builder
	 */
	public DtSketchBuilder withDisplayField(final String displayFieldName) {
		myDisplayFieldName = displayFieldName;
		return this;
	}

	/**
	 * Specifies which field to be used for handle
	 * @param handleFieldName fieldName to use
	 * @return this builder
	 */
	public DtSketchBuilder withHandleField(final String handleFieldName) {
		myHandleFieldName = handleFieldName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtSketch build() {
		Assertion.check().isNull(dtDefinition, "build() already executed");
		//-----
		if (myStereotype == null) {
			myStereotype = myIdField == null ? StudioStereotype.ValueObject : StudioStereotype.Entity;
		}

		final DtSketchField sortField;
		if (mySortFieldName != null) {
			sortField = findFieldByName(mySortFieldName)
					.orElseThrow(() -> new IllegalStateException(StringUtil.format("Sort field '{0}' not found on '{1}'", mySortFieldName, dtDefinition.getName())));
		} else if (myStereotype == StudioStereotype.Fragment) {
			sortField = myFragment.getSortField().orElse(null);
		} else {
			sortField = null;
		}

		final DtSketchField displayField;
		if (myDisplayFieldName != null) {
			displayField = findFieldByName(myDisplayFieldName)
					.orElseThrow(() -> new IllegalStateException(StringUtil.format("Display field '{0}' not found on '{1}'", myDisplayFieldName, dtDefinition.getName())));
		} else if (myStereotype == StudioStereotype.Fragment) {
			displayField = myFragment.getDisplayField().orElse(null);
		} else {
			displayField = null;
		}

		final DtSketchField handleField;
		if (myHandleFieldName != null) {
			handleField = findFieldByName(myHandleFieldName)
					.orElseThrow(() -> new IllegalStateException(StringUtil.format("Handle field '{0}' not found on '{1}'", myHandleFieldName, dtDefinition.getName())));
		} else if (myStereotype == StudioStereotype.Fragment) {
			handleField = myFragment.getHandleField().orElse(null);
		} else {
			handleField = null;
		}

		dtDefinition = new DtSketch(
				myName,
				Optional.ofNullable(myFragment),
				myPackageName,
				myStereotype,
				myFields,
				myDataSpace == null ? "main" : myDataSpace,
				Optional.ofNullable(sortField),
				Optional.ofNullable(displayField),
				Optional.ofNullable(handleField));
		return dtDefinition;
	}

	private Optional<DtSketchField> findFieldByName(final String fieldName) {
		Assertion.check().isNotBlank(fieldName);
		return myFields
				.stream()
				.filter(dtField -> fieldName.equals(dtField.getName()))
				.findFirst();
	}

}
/*
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
package io.vertigo.studio.notebook.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;

/**
 * The DtSketch class defines the sketch of data.
 *
 * @author pchretien, mlaroche
 */
@SkecthPrefix(DtSketch.PREFIX)
public final class DtSketch extends AbstractSketch {
	public static final String PREFIX = "Dt";
	/** the dataSpace must match this pattern. */
	public static final Pattern REGEX_DATA_SPACE = Pattern.compile("[a-z][a-zA-Z0-9]{3,60}");
	public static final String DEFAULT_DATA_SPACE = "main";

	/** if the sketch is a fragment. */
	private final Optional<DtSketch> fragmentOpt;

	/** name of the package. */
	private final String packageName;

	/** List of fields.  */
	private final List<DtSketchField> fields = new ArrayList<>();

	/** Map. (fieldName, DtField). */
	private final Map<String, DtSketchField> mappedFields = new HashMap<>();

	private final StudioStereotype stereotype;

	/** id Field */
	private final Optional<DtSketchField> idFieldOpt;

	private final Optional<DtSketchField> sortFieldOpt;
	private final Optional<DtSketchField> displayFieldOpt;
	private final Optional<DtSketchField> handleFieldOpt;
	private final Optional<DtSketchField> keyFieldOpt;

	private final String dataSpace;

	/**
	 * Constructor.
	 */
	DtSketch(
			final String name,
			final Optional<DtSketch> fragmentOpt,
			final String packageName,
			final StudioStereotype stereotype,
			final List<DtSketchField> dtFields,
			final String dataSpace,
			final Optional<DtSketchField> sortFieldOpt,
			final Optional<DtSketchField> displayFieldOpt,
			final Optional<DtSketchField> handleFieldOpt,
			final Optional<DtSketchField> keyFieldOpt) {
		super(name);
		//---
		Assertion.check()
				.isNotNull(fragmentOpt)
				.isNotNull(stereotype)
				.isNotNull(dtFields)
				.isNotBlank(dataSpace)
				.isTrue(REGEX_DATA_SPACE.matcher(dataSpace).matches(), "dataSpace {0} must match pattern {1}", dataSpace, REGEX_DATA_SPACE)
				.isNotNull(sortFieldOpt)
				.isNotNull(displayFieldOpt)
				.isNotNull(handleFieldOpt);
		//-----
		this.fragmentOpt = fragmentOpt;
		//
		this.stereotype = stereotype;
		this.packageName = packageName;
		DtSketchField id = null;

		this.sortFieldOpt = sortFieldOpt;
		this.displayFieldOpt = displayFieldOpt;
		this.handleFieldOpt = handleFieldOpt;
		this.keyFieldOpt = keyFieldOpt;

		for (final DtSketchField dtField : dtFields) {
			Assertion.check()
					.when(stereotype.isPersistent() && dtField.isPersistent(), () -> Assertion.check()
							.isFalse(dtField.getCardinality().hasMany(),
									"Only non multiple are allowed in entity '{0}'", name));
			if (dtField.getType().isId()) {
				Assertion.check().isNull(id, "Only one ID Field is allowed : {0}", name);
				id = dtField;
			}
			doRegisterDtField(dtField);
		}
		idFieldOpt = Optional.ofNullable(id);
		this.dataSpace = dataSpace;
		//---
		Assertion.check()
				.when(fragmentOpt.isPresent(), () -> Assertion.check()
						.isTrue(StudioStereotype.Fragment == stereotype, "Error on {0} with sterotype {1}, If an object is a fragment then it must have this stereotype", name, stereotype))
				//Persistent => ID
				.when(stereotype.isPersistent(), () -> Assertion.check()
						.isTrue(idFieldOpt.isPresent(), "Error on {0}, If an object is persistent then it must have an ID", name)
						.isTrue(keyFieldOpt.isEmpty(), "Error on {0}, If an object is persistent then it must not have a keyField", name))
				.when(!stereotype.isPersistent(), () -> Assertion.check()
						.isTrue(idFieldOpt.isEmpty(), "Error on {0}, If an object is not persistent then it must have no ID", name));
	}

	/**
	 * Static method factory for DtSketchBuilder
	 * @param name the name of the dtSketch
	 * @return DtSketchBuilder
	 */
	public static DtSketchBuilder builder(final String name) {
		return new DtSketchBuilder(name);
	}

	//TODO A fermer
	void registerDtField(final DtSketchField dtField) {
		Assertion.check()
				.isNotNull(dtField)
				.isFalse(dtField.getType().isId(), "interdit d'ajouter les champs ID ");
		//-----
		doRegisterDtField(dtField);
	}

	private void doRegisterDtField(final DtSketchField dtField) {
		Assertion.check()
				.isNotNull(dtField)
				.isFalse(mappedFields.containsKey(dtField.getName()), "Field {0} déjà enregistré sur {1}", dtField.getName(), this);
		//-----
		fields.add(dtField);
		mappedFields.put(dtField.getName(), dtField);
	}

	public Optional<DtSketch> getFragment() {
		return fragmentOpt;
	}

	/**
	 * @return Stereotype du Dt
	 */
	public StudioStereotype getStereotype() {
		return stereotype;
	}

	/**
	 * @return Nom canonique (i.e. avec le package) de la classe d'implémentation du DtObject
	 */
	public String getClassCanonicalName() {
		return getPackageName() + '.' + getClassSimpleName();
	}

	/**
	 * @return Simple Nom (i.e. sans le package) de la classe d'implémentation du DtObject
	 */
	public String getClassSimpleName() {
		return getLocalName();
	}

	/**
	 * @return the name of the package
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Retourne le champ correspondant SOUS CONDITION qu'il existe sinon assertion.
	 *
	 * @param fieldName Nom du champ
	 * @return Champ correspondant
	 */
	public DtSketchField getField(final String fieldName) {
		Assertion.check().isNotBlank(fieldName);
		//-----
		final DtSketchField dtField = mappedFields.get(fieldName);
		//-----
		Assertion.check().isNotNull(dtField, "field '{0}' not found on '{1}'. Available fields are :{2}", fieldName, this, mappedFields.keySet());
		return dtField;
	}

	/**
	 * @param fieldName FieldName
	 * @return if this field exists in this DtSketch
	 */
	public boolean contains(final String fieldName) {
		Assertion.check().isNotNull(fieldName);
		//-----
		return mappedFields.containsKey(fieldName);
	}

	/**
	 * @return Collection des champs.
	 */
	public List<DtSketchField> getFields() {
		return Collections.unmodifiableList(fields);
	}

	/**
	 * @return Champ identifiant l'identifiant
	 */
	public Optional<DtSketchField> getIdField() {
		return idFieldOpt;
	}

	/**
	 * Gestion de la persistance.
	 * @return Si la définition est persistée.
	 */
	public boolean isPersistent() {
		return stereotype.isPersistent();
	}

	/**
	 * @return Champ représentant l'affichage
	 */
	public Optional<DtSketchField> getDisplayField() {
		return displayFieldOpt;
	}

	/**
	 * @return Champ représentant le tri
	 */
	public Optional<DtSketchField> getSortField() {
		return sortFieldOpt;
	}

	/**
	 * @return Champ représentant le handle
	 */
	public Optional<DtSketchField> getHandleField() {
		return handleFieldOpt;
	}

	/**
	 * @return Champ représentant la clé
	 */
	public Optional<DtSketchField> getKeyField() {
		return keyFieldOpt;
	}

	/**
	 * @return the dataSpace
	 */
	public String getDataSpace() {
		return dataSpace;
	}
}

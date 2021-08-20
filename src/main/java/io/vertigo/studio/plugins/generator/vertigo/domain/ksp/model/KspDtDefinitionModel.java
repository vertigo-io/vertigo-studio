/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.ksp.model;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.DtSketchField.FieldType;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.plugins.generator.vertigo.VertigoConstants.VertigoClassNames;

/**
 * Model used by FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class KspDtDefinitionModel {
	private final DtSketch dtSketch;
	private final List<KspDtFieldModel> idFieldModels = new ArrayList<>();
	private final List<KspDtFieldModel> dataFieldModels = new ArrayList<>();
	private final List<KspDtFieldModel> computedFieldModels = new ArrayList<>();

	/**
	 * Constructeur.
	 *
	 * @param dtSketch dtSketch de l'objet à générer
	 */
	public KspDtDefinitionModel(final DtSketch dtSketch) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.dtSketch = dtSketch;

		for (final DtSketchField dtField : dtSketch.getFields()) {
			final KspDtFieldModel dtFieldModel = new KspDtFieldModel(dtSketch, dtField);
			if (FieldType.COMPUTED == dtField.getType()) {
				computedFieldModels.add(dtFieldModel);
			} else if (FieldType.DATA == dtField.getType()) {
				dataFieldModels.add(dtFieldModel);
			} else if (FieldType.ID == dtField.getType()) {
				idFieldModels.add(dtFieldModel);
			}
		}

	}

	/**
	 * @return DT définition
	 */
	public DtSketch getDtSketch() {
		return dtSketch;
	}

	/**
	 * Retourne le nom local de la definition (const case, sans prefix)
	 * @return Simple Nom (i.e. sans le package) de la definition du DtObject
	 */
	public String getName() {
		return "Dt" + dtSketch.getLocalName();
	}

	/**
	 * @return Nom du package
	 */
	public String getPackageName() {
		return dtSketch.getPackageName();
	}

	public boolean isFragment() {
		return dtSketch.getStereotype() == StudioStereotype.Fragment;
	}

	public String getEntityClassSimpleName() {
		return dtSketch.getFragment().get().getClassSimpleName();
	}

	/**
	 * @return Nom simple de l'nterface associé au Sterotype de l'objet (DtObject, DtMasterData ou KeyConcept)
	 */
	public String getStereotypeInterfaceName() {
		if (dtSketch.getStereotype() == StudioStereotype.Fragment) {
			return getStereotypeClass().getSimpleName() + "<" + getEntityClassSimpleName() + ">";
		}
		return getStereotypeClass().getSimpleName();
	}

	private VertigoClassNames getStereotypeClass() {
		switch (dtSketch.getStereotype()) {
			case Entity:
				return VertigoClassNames.Entity;
			case ValueObject:
				return VertigoClassNames.DtObject;
			case MasterData:
				return VertigoClassNames.DtMasterData;
			case StaticMasterData:
				return VertigoClassNames.DtStaticMasterData;
			case KeyConcept:
				return VertigoClassNames.KeyConcept;
			case Fragment:
				return VertigoClassNames.Fragment;
			default:
				throw new IllegalArgumentException("Stereotype " + dtSketch.getStereotype().name() + " non géré");
		}
	}

	/**
	 * @return Liste de champs
	 */
	public List<KspDtFieldModel> getDataFields() {
		return dataFieldModels;
	}

	/**
	 * @return Liste des champs calculés
	 */
	public List<KspDtFieldModel> getComputedFields() {
		return computedFieldModels;
	}

	public List<KspDtFieldModel> getIdFields() {
		return idFieldModels;
	}

}

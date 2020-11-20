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
package io.vertigo.studio.plugins.generator.mermaid.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.DtSketchField.FieldType;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.plugins.generator.vertigo.VertigoConstants.VertigoClassNames;

/**
 * Model used by FreeMarker.
 *
 * @author mlaroche
 */
public final class MermaidDtModel {
	private final DtSketch dtSketch;
	private final List<MermaidDtFieldModel> dtFieldModels = new ArrayList<>();
	private final List<MermaidDtFieldModel> dtAllFieldModels = new ArrayList<>();
	private final List<MermaidDtFieldModel> dtComputedFieldModels = new ArrayList<>();
	private final List<MermaidSimpleAssociationModel> simpleAssociationModels;
	private final List<MermaidNNAssociationModel> nnAssociationModels;

	/**
	 * Constructeur.
	 *
	 * @param dtSketch dtSketch de l'objet à générer
	 */
	public MermaidDtModel(final DtSketch dtSketch, final List<AssociationSketch> associationSketchs, final Function<String, String> classNameFromDt) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.dtSketch = dtSketch;

		for (final DtSketchField dtField : dtSketch.getFields()) {
			final MermaidDtFieldModel dtFieldModel = new MermaidDtFieldModel(dtSketch, dtField, classNameFromDt);
			dtAllFieldModels.add(dtFieldModel);
			if (FieldType.COMPUTED == dtField.getType()) {
				dtComputedFieldModels.add(dtFieldModel);
			} else {
				dtFieldModels.add(dtFieldModel);
			}
		}
		simpleAssociationModels = associationSketchs.stream()
				.filter(associationSketch -> associationSketch instanceof AssociationSimpleSketch)
				.map(associationSketch -> (AssociationSimpleSketch) associationSketch)
				.filter(associationSimpleSketch -> associationSimpleSketch.getForeignAssociationNode().getDtSketch().getKey().equals(dtSketch.getKey())) // only our ones
				.map(MermaidSimpleAssociationModel::new)
				.collect(Collectors.toList());

		nnAssociationModels = associationSketchs.stream()
				.filter(associationSketch -> associationSketch instanceof AssociationNNSketch)
				.map(associationSketch -> (AssociationNNSketch) associationSketch)
				.map(MermaidNNAssociationModel::new)
				.collect(Collectors.toList());

	}

	/**
	 * @return DT définition
	 */
	public DtSketch getDtSketch() {
		return dtSketch;
	}

	/**
	 * @return Nom canonique (i.e. avec le package) de la classe d'implémentation du DtObject
	 */
	public String getClassCanonicalName() {
		return dtSketch.getClassCanonicalName();
	}

	/**
	 * @return Simple Nom (i.e. sans le package) de la classe d'implémentation du DtObject
	 */
	public String getClassSimpleName() {
		return dtSketch.getClassSimpleName();
	}

	/**
	 * Retourne le nom local de la definition (const case, sans prefix)
	 * @return Simple Nom (i.e. sans le package) de la definition du DtObject
	 */
	public String getLocalName() {
		return dtSketch.getLocalName();
	}

	/**
	 * @return Nom du package
	 */
	public String getPackageName() {
		return dtSketch.getPackageName();
	}

	public String getStereotypeClassCanonicalName() {
		return getStereotypeClass().getClassName();
	}

	public boolean isEntity() {
		return dtSketch.getStereotype() == StudioStereotype.Entity
				|| dtSketch.getStereotype() == StudioStereotype.KeyConcept
				|| dtSketch.getStereotype() == StudioStereotype.Fragment
				|| dtSketch.getStereotype() == StudioStereotype.MasterData
				|| dtSketch.getStereotype() == StudioStereotype.StaticMasterData;
	}

	/**
	 * @return Nom simple de l'nterface associé au Sterotype de l'objet (DtObject, DtMasterData ou KeyConcept)
	 */
	public String getStereotypeInterfaceName() {
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
	public List<MermaidDtFieldModel> getFields() {
		return dtFieldModels;
	}

	/**
	 * @return Liste de tous les champs
	 */
	public List<MermaidDtFieldModel> getAllFields() {
		return dtAllFieldModels;
	}

	/**
	 * @return Liste des champs calculés
	 */
	public List<MermaidDtFieldModel> getDtComputedFields() {
		return dtComputedFieldModels;
	}

	public List<MermaidSimpleAssociationModel> getSimpleAssociations() {
		return simpleAssociationModels;
	}

	public List<MermaidNNAssociationModel> getNnAssociations() {
		return nnAssociationModels;
	}

}

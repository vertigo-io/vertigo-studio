/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.DtSketchField.FieldType;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.plugins.generator.vertigo.VertigoConstants.VertigoClassNames;

/**
 * Model used by FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class StudioDtModel {
	private final DtSketch dtSketch;
	private final List<StudioDtFieldModel> dtFieldModels = new ArrayList<>();
	private final List<StudioDtFieldModel> dtAllFieldModels = new ArrayList<>();
	private final List<StudioDtFieldModel> dtComputedFieldModels = new ArrayList<>();
	private final List<StudioAssociationModel> associationModels = new ArrayList<>();

	/**
	 * Constructeur.
	 *
	 * @param dtSketch dtSketch de l'objet à générer
	 */
	public StudioDtModel(final DtSketch dtSketch, final List<? extends AssociationSketch> associationSketches, final Function<String, String> classNameFromDt) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.dtSketch = dtSketch;

		for (final DtSketchField dtField : dtSketch.getFields()) {
			final StudioDtFieldModel dtFieldModel = new StudioDtFieldModel(dtSketch, dtField, associationSketches, classNameFromDt);
			dtAllFieldModels.add(dtFieldModel);
			if (FieldType.COMPUTED == dtField.getType()) {
				dtComputedFieldModels.add(dtFieldModel);
			} else {
				dtFieldModels.add(dtFieldModel);
			}
		}

		for (final AssociationSketch associationDefinition : associationSketches) {
			if (associationDefinition.getAssociationNodeA().getDtSketch().getKey().equals(dtSketch.getKey())) {
				associationModels.add(new StudioAssociationModel(associationDefinition, associationDefinition.getAssociationNodeB()));
			}
			if (associationDefinition.getAssociationNodeB().getDtSketch().getKey().equals(dtSketch.getKey())) {
				associationModels.add(new StudioAssociationModel(associationDefinition, associationDefinition.getAssociationNodeA()));
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
	public List<StudioDtFieldModel> getFields() {
		return dtFieldModels;
	}

	/**
	 * @return Liste de tous les champs
	 */
	public List<StudioDtFieldModel> getAllFields() {
		return dtAllFieldModels;
	}

	/**
	 * @return Liste des champs calculés
	 */
	public List<StudioDtFieldModel> getDtComputedFields() {
		return dtComputedFieldModels;
	}

	/**
	 * @return Liste des associations
	 */
	public List<StudioAssociationModel> getAssociations() {
		return associationModels;
	}

	public boolean containsAccessor() {
		return dtSketch.getStereotype() != StudioStereotype.Fragment &&
				associationModels
						.stream()
						//only simple
						.filter(StudioAssociationModel::isSimple)
						.map(associationModel -> (AssociationSimpleSketch) associationModel.getSketch())
						.filter(association -> association.getForeignAssociationNode().getDtSketch().getKey().equals(dtSketch.getKey())) // only when we are on the foreign node with a fk field
						.anyMatch(association -> association.getPrimaryAssociationNode().getDtSketch().getStereotype() != StudioStereotype.StaticMasterData); // any that IS NOT a static master data

	}

	public boolean containsEnumAccessor() {
		return dtSketch.getStereotype() != StudioStereotype.Fragment &&
				associationModels
						.stream()
						//only simple
						.filter(StudioAssociationModel::isSimple)
						.map(associationModel -> (AssociationSimpleSketch) associationModel.getSketch())
						.filter(association -> association.getForeignAssociationNode().getDtSketch().getKey().equals(dtSketch.getKey())) // only when we are on the primary node
						.anyMatch(association -> association.getPrimaryAssociationNode().getDtSketch().getStereotype() == StudioStereotype.StaticMasterData); // any that IS  a static master data

	}

	public boolean containsListAccessor() {
		return associationModels
				.stream()
				//only multiples
				.filter(StudioAssociationModel::isMultiple)
				//simple navigable ou nn
				.anyMatch(associationModel -> !associationModel.isSimple() || associationModel.isNavigable());

	}
}

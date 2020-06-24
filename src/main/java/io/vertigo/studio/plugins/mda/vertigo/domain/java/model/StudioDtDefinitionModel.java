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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.domain.StudioDtField;
import io.vertigo.studio.metamodel.domain.StudioDtField.FieldType;
import io.vertigo.studio.metamodel.domain.StudioStereotype;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationDefinition;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationSimpleDefinition;
import io.vertigo.studio.plugins.mda.vertigo.VertigoConstants.VertigoClassNames;

/**
 * Model used by FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class StudioDtDefinitionModel {
	private final StudioDtDefinition dtDefinition;
	private final List<StudioDtFieldModel> dtFieldModels = new ArrayList<>();
	private final List<StudioDtFieldModel> dtAllFieldModels = new ArrayList<>();
	private final List<StudioDtFieldModel> dtComputedFieldModels = new ArrayList<>();
	private final List<StudioAssociationModel> associationModels = new ArrayList<>();

	/**
	 * Constructeur.
	 *
	 * @param dtDefinition DtDefinition de l'objet à générer
	 */
	public StudioDtDefinitionModel(final StudioDtDefinition dtDefinition, final List<? extends StudioAssociationDefinition> associationDefinitions, final Function<String, String> classNameFromDt) {
		Assertion.check().isNotNull(dtDefinition);
		//-----
		this.dtDefinition = dtDefinition;

		for (final StudioDtField dtField : dtDefinition.getFields()) {
			final StudioDtFieldModel dtFieldModel = new StudioDtFieldModel(dtDefinition, dtField, associationDefinitions, classNameFromDt);
			dtAllFieldModels.add(dtFieldModel);
			if (FieldType.COMPUTED == dtField.getType()) {
				dtComputedFieldModels.add(dtFieldModel);
			} else {
				dtFieldModels.add(dtFieldModel);
			}
		}

		for (final StudioAssociationDefinition associationDefinition : associationDefinitions) {
			if (associationDefinition.getAssociationNodeA().getDtDefinition().getName().equals(dtDefinition.getName())) {
				associationModels.add(new StudioAssociationModel(associationDefinition, associationDefinition.getAssociationNodeB()));
			}
			if (associationDefinition.getAssociationNodeB().getDtDefinition().getName().equals(dtDefinition.getName())) {
				associationModels.add(new StudioAssociationModel(associationDefinition, associationDefinition.getAssociationNodeA()));
			}
		}

	}

	/**
	 * @return DT définition
	 */
	public StudioDtDefinition getDtDefinition() {
		return dtDefinition;
	}

	/**
	 * @return Nom canonique (i.e. avec le package) de la classe d'implémentation du DtObject
	 */
	public String getClassCanonicalName() {
		return dtDefinition.getClassCanonicalName();
	}

	/**
	 * @return Simple Nom (i.e. sans le package) de la classe d'implémentation du DtObject
	 */
	public String getClassSimpleName() {
		return dtDefinition.getClassSimpleName();
	}

	/**
	 * Retourne le nom local de la definition (const case, sans prefix)
	 * @return Simple Nom (i.e. sans le package) de la definition du DtObject
	 */
	public String getLocalName() {
		return dtDefinition.getLocalName();
	}

	/**
	 * @return Nom du package
	 */
	public String getPackageName() {
		return dtDefinition.getPackageName();
	}

	public String getStereotypeClassCanonicalName() {
		return getStereotypeClass().getClassName();
	}

	public boolean isEntity() {
		return dtDefinition.getStereotype() == StudioStereotype.Entity
				|| dtDefinition.getStereotype() == StudioStereotype.KeyConcept
				|| dtDefinition.getStereotype() == StudioStereotype.Fragment
				|| dtDefinition.getStereotype() == StudioStereotype.MasterData
				|| dtDefinition.getStereotype() == StudioStereotype.StaticMasterData;
	}

	public boolean isFragment() {
		return dtDefinition.getStereotype() == StudioStereotype.Fragment;
	}

	public String getEntityClassSimpleName() {
		return dtDefinition.getFragment().get().getClassSimpleName();
	}

	/**
	 * @return Nom simple de l'nterface associé au Sterotype de l'objet (DtObject, DtMasterData ou KeyConcept)
	 */
	public String getStereotypeInterfaceName() {
		if (dtDefinition.getStereotype() == StudioStereotype.Fragment) {
			return getStereotypeClass().getSimpleName() + "<" + getEntityClassSimpleName() + ">";
		}
		return getStereotypeClass().getSimpleName();
	}

	private VertigoClassNames getStereotypeClass() {
		switch (dtDefinition.getStereotype()) {
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
				throw new IllegalArgumentException("Stereotype " + dtDefinition.getStereotype().name() + " non géré");
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
		return dtDefinition.getStereotype() != StudioStereotype.Fragment &&
				associationModels
						.stream()
						//only simple
						.filter(StudioAssociationModel::isSimple)
						.map(associationModel -> (StudioAssociationSimpleDefinition) associationModel.getDefinition())
						.filter(association -> association.getForeignAssociationNode().getDtDefinition().getName().equals(dtDefinition.getName())) // only when we are on the foreign node with a fk field
						.anyMatch(association -> association.getPrimaryAssociationNode().getDtDefinition().getStereotype() != StudioStereotype.StaticMasterData); // any that IS NOT a static master data

	}

	public boolean containsEnumAccessor() {
		return dtDefinition.getStereotype() != StudioStereotype.Fragment &&
				associationModels
						.stream()
						//only simple
						.filter(StudioAssociationModel::isSimple)
						.map(associationModel -> (StudioAssociationSimpleDefinition) associationModel.getDefinition())
						.filter(association -> association.getForeignAssociationNode().getDtDefinition().getName().equals(dtDefinition.getName())) // only when we are on the primary node
						.anyMatch(association -> association.getPrimaryAssociationNode().getDtDefinition().getStereotype() == StudioStereotype.StaticMasterData); // any that IS  a static master data

	}

	public boolean containsListAccessor() {
		return associationModels
				.stream()
				//only multiples
				.filter(StudioAssociationModel::isMultiple)
				//simple navigable ou nn
				.anyMatch(associationModel -> (associationModel.isSimple() && associationModel.isNavigable()) || !associationModel.isSimple());

	}
}

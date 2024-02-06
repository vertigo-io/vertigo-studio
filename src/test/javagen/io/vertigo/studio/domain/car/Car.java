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
package io.vertigo.studio.domain.car;

import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.data.model.Entity;
import io.vertigo.datamodel.data.model.UID;
import io.vertigo.datamodel.data.stereotype.Field;
import io.vertigo.datamodel.data.util.DataUtil;
import io.vertigo.datastore.impl.entitystore.EnumStoreVAccessor;
import io.vertigo.datastore.impl.entitystore.StoreListVAccessor;
import io.vertigo.datastore.impl.entitystore.StoreVAccessor;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class Car implements Entity {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String manufacturer;
	private String model;
	private String description;
	private Integer year;
	private Integer kilo;
	private Integer price;
	private java.math.BigDecimal consommation;

	@io.vertigo.datamodel.data.stereotype.Association(
			name = "AMtyCar",
			fkFieldName = "mtyCd",
			primaryDtDefinitionName = "DtMotorType",
			primaryIsNavigable = true,
			primaryRole = "MotorType",
			primaryLabel = "Motor type",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DtCar",
			foreignIsNavigable = false,
			foreignRole = "Car",
			foreignLabel = "Car",
			foreignMultiplicity = "0..*")
	private final EnumStoreVAccessor<io.vertigo.studio.domain.car.MotorType, io.vertigo.studio.domain.car.MotorTypeEnum> mtyCdAccessor = new EnumStoreVAccessor<>(io.vertigo.studio.domain.car.MotorType.class, "MotorType", io.vertigo.studio.domain.car.MotorTypeEnum.class);

	@io.vertigo.datamodel.data.stereotype.Association(
			name = "AFamCarFamille",
			fkFieldName = "famId",
			primaryDtDefinitionName = "DtFamille",
			primaryIsNavigable = false,
			primaryRole = "Famille",
			primaryLabel = "Famille",
			primaryMultiplicity = "1..1",
			foreignDtDefinitionName = "DtCar",
			foreignIsNavigable = true,
			foreignRole = "VoituresFamille",
			foreignLabel = "Voitures de la famille",
			foreignMultiplicity = "0..*")
	private final StoreVAccessor<io.vertigo.studio.domain.famille.Famille> famIdAccessor = new StoreVAccessor<>(io.vertigo.studio.domain.famille.Famille.class, "Famille");

	@io.vertigo.datamodel.data.stereotype.AssociationNN(
			name = "AnnFamCarLocation",
			tableName = "FAM_CAR_LOCATION",
			dtDefinitionA = "DtFamille",
			dtDefinitionB = "DtCar",
			navigabilityA = false,
			navigabilityB = true,
			roleA = "Famille",
			roleB = "VoituresLocation",
			labelA = "Famille",
			labelB = "Voitures de location")
	private final StoreListVAccessor<io.vertigo.studio.domain.famille.Famille> familleAccessor = new StoreListVAccessor<>(this, "AnnFamCarLocation", "Famille");

	/** {@inheritDoc} */
	@Override
	public UID<Car> getUID() {
		return UID.of(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'identifiant de la voiture'.
	 * @return Long id <b>Obligatoire</b>
	 */
	@Field(smartType = "STyId", type = "ID", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "identifiant de la voiture")
	public Long getId() {
		return id;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'identifiant de la voiture'.
	 * @param id Long <b>Obligatoire</b>
	 */
	public void setId(final Long id) {
		this.id = id;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Constructeur'.
	 * @return String manufacturer <b>Obligatoire</b>
	 */
	@Field(smartType = "STyKeyword", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Constructeur")
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Constructeur'.
	 * @param manufacturer String <b>Obligatoire</b>
	 */
	public void setManufacturer(final String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Modèle'.
	 * @return String model <b>Obligatoire</b>
	 */
	@Field(smartType = "STyFullText", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Modèle")
	public String getModel() {
		return model;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Modèle'.
	 * @param model String <b>Obligatoire</b>
	 */
	public void setModel(final String model) {
		this.model = model;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Descriptif'.
	 * @return String description <b>Obligatoire</b>
	 */
	@Field(smartType = "STyFullText", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Descriptif")
	public String getDescription() {
		return description;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Descriptif'.
	 * @param description String <b>Obligatoire</b>
	 */
	public void setDescription(final String description) {
		this.description = description;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Année'.
	 * @return Integer year <b>Obligatoire</b>
	 */
	@Field(smartType = "STyInteger", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Année")
	public Integer getYear() {
		return year;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Année'.
	 * @param year Integer <b>Obligatoire</b>
	 */
	public void setYear(final Integer year) {
		this.year = year;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Kilométrage'.
	 * @return Integer kilo <b>Obligatoire</b>
	 */
	@Field(smartType = "STyInteger", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Kilométrage")
	public Integer getKilo() {
		return kilo;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Kilométrage'.
	 * @param kilo Integer <b>Obligatoire</b>
	 */
	public void setKilo(final Integer kilo) {
		this.kilo = kilo;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Prix'.
	 * @return Integer price <b>Obligatoire</b>
	 */
	@Field(smartType = "STyInteger", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Prix")
	public Integer getPrice() {
		return price;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Prix'.
	 * @param price Integer <b>Obligatoire</b>
	 */
	public void setPrice(final Integer price) {
		this.price = price;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Consommation'.
	 * @return BigDecimal consommation <b>Obligatoire</b>
	 */
	@Field(smartType = "STyConso", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Consommation")
	public java.math.BigDecimal getConsommation() {
		return consommation;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Consommation'.
	 * @param consommation BigDecimal <b>Obligatoire</b>
	 */
	public void setConsommation(final java.math.BigDecimal consommation) {
		this.consommation = consommation;
	}
	
	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Motor type'.
	 * @return String mtyCd
	 */
	@io.vertigo.datamodel.data.stereotype.ForeignKey(smartType = "STyString", label = "Motor type", fkDefinition = "DtMotorType" )
	public String getMtyCd() {
		return (String) mtyCdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Motor type'.
	 * @param mtyCd String
	 */
	public void setMtyCd(final String mtyCd) {
		mtyCdAccessor.setId(mtyCd);
	}
	
	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Famille'.
	 * @return Long famId <b>Obligatoire</b>
	 */
	@io.vertigo.datamodel.data.stereotype.ForeignKey(smartType = "STyId", label = "Famille", fkDefinition = "DtFamille", cardinality = io.vertigo.core.lang.Cardinality.ONE )
	public Long getFamId() {
		return (Long) famIdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Famille'.
	 * @param famId Long <b>Obligatoire</b>
	 */
	public void setFamId(final Long famId) {
		famIdAccessor.setId(famId);
	}

 	/**
	 * Association : Famille.
	 * @return l'accesseur vers la propriété 'Famille'
	 */
	public StoreVAccessor<io.vertigo.studio.domain.famille.Famille> famille() {
		return famIdAccessor;
	}

 	/**
	 * Association : Motor type.
	 * @return l'accesseur vers la propriété 'Motor type'
	 */
	public EnumStoreVAccessor<io.vertigo.studio.domain.car.MotorType, io.vertigo.studio.domain.car.MotorTypeEnum> motorType() {
		return mtyCdAccessor;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DataUtil.toString(this);
	}
}

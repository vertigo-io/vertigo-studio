/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.domain.security;

import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.data.model.KeyConcept;
import io.vertigo.datamodel.data.model.UID;
import io.vertigo.datamodel.data.stereotype.Field;
import io.vertigo.datamodel.data.util.DataModelUtil;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class Record implements KeyConcept {
	private static final long serialVersionUID = 1L;

	private Long dosId;
	private Long regId;
	private Long depId;
	private Long comId;
	private Long typId;
	private String title;
	private java.math.BigDecimal amount;
	private Long utiIdOwner;
	private String etaCd;

	/** {@inheritDoc} */
	@Override
	public UID<Record> getUID() {
		return UID.of(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id'.
	 * @return Long dosId <b>Obligatoire</b>
	 */
	@Field(smartType = "STyId", type = "ID", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Id")
	public Long getDosId() {
		return dosId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id'.
	 * @param dosId Long <b>Obligatoire</b>
	 */
	public void setDosId(final Long dosId) {
		this.dosId = dosId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Region'.
	 * @return Long regId
	 */
	@Field(smartType = "STyId", label = "Region")
	public Long getRegId() {
		return regId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Region'.
	 * @param regId Long
	 */
	public void setRegId(final Long regId) {
		this.regId = regId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Departement'.
	 * @return Long depId
	 */
	@Field(smartType = "STyId", label = "Departement")
	public Long getDepId() {
		return depId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Departement'.
	 * @param depId Long
	 */
	public void setDepId(final Long depId) {
		this.depId = depId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Commune'.
	 * @return Long comId
	 */
	@Field(smartType = "STyId", label = "Commune")
	public Long getComId() {
		return comId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Commune'.
	 * @param comId Long
	 */
	public void setComId(final Long comId) {
		this.comId = comId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Record type'.
	 * @return Long typId <b>Obligatoire</b>
	 */
	@Field(smartType = "STyId", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Record type")
	public Long getTypId() {
		return typId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Record type'.
	 * @param typId Long <b>Obligatoire</b>
	 */
	public void setTypId(final Long typId) {
		this.typId = typId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Title'.
	 * @return String title <b>Obligatoire</b>
	 */
	@Field(smartType = "STyString", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Title")
	public String getTitle() {
		return title;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Title'.
	 * @param title String <b>Obligatoire</b>
	 */
	public void setTitle(final String title) {
		this.title = title;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Amount'.
	 * @return BigDecimal amount <b>Obligatoire</b>
	 */
	@Field(smartType = "STyConso", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Amount")
	public java.math.BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Amount'.
	 * @param amount BigDecimal <b>Obligatoire</b>
	 */
	public void setAmount(final java.math.BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Owner'.
	 * @return Long utiIdOwner <b>Obligatoire</b>
	 */
	@Field(smartType = "STyId", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Owner")
	public Long getUtiIdOwner() {
		return utiIdOwner;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Owner'.
	 * @param utiIdOwner Long <b>Obligatoire</b>
	 */
	public void setUtiIdOwner(final Long utiIdOwner) {
		this.utiIdOwner = utiIdOwner;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'State'.
	 * @return String etaCd <b>Obligatoire</b>
	 */
	@Field(smartType = "STyString", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "State")
	public String getEtaCd() {
		return etaCd;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'State'.
	 * @param etaCd String <b>Obligatoire</b>
	 */
	public void setEtaCd(final String etaCd) {
		this.etaCd = etaCd;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DataModelUtil.toString(this);
	}
}

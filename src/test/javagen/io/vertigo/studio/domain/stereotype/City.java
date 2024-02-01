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
package io.vertigo.studio.domain.stereotype;

import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.data.model.DtMasterData;
import io.vertigo.datamodel.data.model.UID;
import io.vertigo.datamodel.data.stereotype.Field;
import io.vertigo.datamodel.data.util.DtObjectUtil;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class City implements DtMasterData {
	private static final long serialVersionUID = 1L;

	private Long citId;
	private String label;
	private String postalCode;

	/** {@inheritDoc} */
	@Override
	public UID<City> getUID() {
		return UID.of(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'id'.
	 * @return Long citId <b>Obligatoire</b>
	 */
	@Field(smartType = "STyId", type = "ID", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "id")
	public Long getCitId() {
		return citId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'id'.
	 * @param citId Long <b>Obligatoire</b>
	 */
	public void setCitId(final Long citId) {
		this.citId = citId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Label'.
	 * @return String label <b>Obligatoire</b>
	 */
	@Field(smartType = "STyFullText", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Label")
	public String getLabel() {
		return label;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Label'.
	 * @param label String <b>Obligatoire</b>
	 */
	public void setLabel(final String label) {
		this.label = label;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Postal code'.
	 * @return String postalCode <b>Obligatoire</b>
	 */
	@Field(smartType = "STyKeyword", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Postal code")
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Postal code'.
	 * @param postalCode String <b>Obligatoire</b>
	 */
	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}

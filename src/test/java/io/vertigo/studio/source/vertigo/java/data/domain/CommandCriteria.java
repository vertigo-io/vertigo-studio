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
package io.vertigo.studio.source.vertigo.java.data.domain;

import io.vertigo.datamodel.data.model.DataObject;
import io.vertigo.datamodel.data.stereotype.Field;
import io.vertigo.datamodel.data.util.DataModelUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données Command
 */
public final class CommandCriteria implements DataObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cmdId;
	private Long ctyId;
	private Long citId;

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'id'.
	 * @return String cmdId <b>Obligatoire</b>
	 */
	@Field(smartType = "DoFullText", label = "Numero de commande")
	public String getCmdId() {
		return cmdId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'id'.
	 * @param cmdId Long <b>Obligatoire</b>
	 */
	public void setCmdId(final String cmdId) {
		this.cmdId = cmdId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Command type'.
	 * @return Long ctyId
	 */
	@Field(smartType = "DoIdentifiant", type = "FOREIGN_KEY", label = "Command type")
	public Long getCtyId() {
		return ctyId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Command type'.
	 * @param ctyId Long
	 */
	public void setCtyId(final Long ctyId) {
		this.ctyId = ctyId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'City'.
	 * @return Long citId
	 */
	@Field(smartType = "DoIdentifiant", type = "FOREIGN_KEY", label = "City")
	public Long getCitId() {
		return citId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'City'.
	 * @param citId Long
	 */
	public void setCitId(final Long citId) {
		this.citId = citId;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DataModelUtil.toString(this);
	}
}

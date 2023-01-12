/**
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
package io.vertigo.studio.source.vertigo.java.data.domain;

import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.structure.model.Entity;
import io.vertigo.datamodel.structure.model.UID;
import io.vertigo.datamodel.structure.model.VAccessor;
import io.vertigo.datamodel.structure.stereotype.Field;
import io.vertigo.datamodel.structure.util.DtObjectUtil;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class Attachment implements Entity {
	private static final long serialVersionUID = 1L;

	private Long attId;
	private String url;

	@io.vertigo.datamodel.structure.stereotype.Association(
			name = "ACmdAtt",
			fkFieldName = "cmdId",
			primaryDtDefinitionName = "DtCommand",
			primaryIsNavigable = true,
			primaryRole = "Command",
			primaryLabel = "Command",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DtAttachment",
			foreignIsNavigable = true,
			foreignRole = "Attachment",
			foreignLabel = "Attachment",
			foreignMultiplicity = "0..*")
	private final VAccessor<Command> cmdIdAccessor = new VAccessor<>(Command.class, "Command");

	/** {@inheritDoc} */
	@Override
	public UID<Attachment> getUID() {
		return UID.of(this);
	}

	/**
	 * Champ : id.
	 * Récupère la valeur de la propriété 'id'.
	 * @return Long attId <b>Obligatoire</b>
	 */
	@Field(smartType = "DoId", type = "ID", cardinality = Cardinality.ONE, label = "id")
	public Long getAttId() {
		return attId;
	}

	/**
	 * Champ : id.
	 * Définit la valeur de la propriété 'id'.
	 * @param attId Long <b>Obligatoire</b>
	 */
	public void setAttId(final Long attId) {
		this.attId = attId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Url'.
	 * @return String url <b>Obligatoire</b>
	 */
	@Field(smartType = "DoKeyword", cardinality = Cardinality.ONE, label = "Url")
	public String getUrl() {
		return url;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Url'.
	 * @param url String <b>Obligatoire</b>
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Command'.
	 * @return Long cmdId
	 */
	@Field(smartType = "DoId", type = "FOREIGN_KEY", label = "Command")
	public Long getCmdId() {
		return (Long) cmdIdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Command'.
	 * @param cmdId Long
	 */
	public void setCmdId(final Long cmdId) {
		cmdIdAccessor.setId(cmdId);
	}

	/**
	 * Association : Command.
	 * @return l'accesseur vers la propriété 'Command'
	 */
	public VAccessor<Command> command() {
		return cmdIdAccessor;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}

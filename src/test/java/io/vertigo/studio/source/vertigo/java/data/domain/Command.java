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
import io.vertigo.datamodel.structure.model.KeyConcept;
import io.vertigo.datamodel.structure.model.ListVAccessor;
import io.vertigo.datamodel.structure.model.UID;
import io.vertigo.datamodel.structure.model.VAccessor;
import io.vertigo.datamodel.structure.stereotype.Field;
import io.vertigo.datamodel.structure.util.DtObjectUtil;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class Command implements KeyConcept {
	private static final long serialVersionUID = 1L;

	private Long cmdId;

	@io.vertigo.datamodel.structure.stereotype.Association(
			name = "ACtyCmd",
			fkFieldName = "ctyId",
			primaryDtDefinitionName = "DtCommandType",
			primaryIsNavigable = true,
			primaryRole = "CommandType",
			primaryLabel = "Command type",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DtCommand",
			foreignIsNavigable = false,
			foreignRole = "Command",
			foreignLabel = "Command",
			foreignMultiplicity = "0..*")
	private final VAccessor<CommandType> ctyIdAccessor = new VAccessor<>(CommandType.class, "CommandType");

	@io.vertigo.datamodel.structure.stereotype.Association(
			name = "ACitCmd",
			fkFieldName = "citId",
			primaryDtDefinitionName = "DtCity",
			primaryIsNavigable = true,
			primaryRole = "City",
			primaryLabel = "City",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DtCommand",
			foreignIsNavigable = false,
			foreignRole = "Command",
			foreignLabel = "Command",
			foreignMultiplicity = "0..*")
	private final VAccessor<City> citIdAccessor = new VAccessor<>(City.class, "City");

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
	private final ListVAccessor<Attachment> attachmentAccessor = new ListVAccessor<>(this, "ACmdAtt", "Attachment");

	/** {@inheritDoc} */
	@Override
	public UID<Command> getUID() {
		return UID.of(this);
	}

	/**
	 * Champ : id.
	 * Récupère la valeur de la propriété 'id'.
	 * @return Long cmdId <b>Obligatoire</b>
	 */
	@Field(smartType = "DoId", type = "ID", cardinality = Cardinality.ONE, label = "id")
	public Long getCmdId() {
		return cmdId;
	}

	/**
	 * Champ : id.
	 * Définit la valeur de la propriété 'id'.
	 * @param cmdId Long <b>Obligatoire</b>
	 */
	public void setCmdId(final Long cmdId) {
		this.cmdId = cmdId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Command type'.
	 * @return Long ctyId
	 */
	@Field(smartType = "DoId", type = "FOREIGN_KEY", label = "Command type")
	public Long getCtyId() {
		return (Long) ctyIdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Command type'.
	 * @param ctyId Long
	 */
	public void setCtyId(final Long ctyId) {
		ctyIdAccessor.setId(ctyId);
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'City'.
	 * @return Long citId
	 */
	@Field(smartType = "DoId", type = "FOREIGN_KEY", label = "City")
	public Long getCitId() {
		return (Long) citIdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'City'.
	 * @param citId Long
	 */
	public void setCitId(final Long citId) {
		citIdAccessor.setId(citId);
	}

	/**
	 * Association : City.
	 * @return l'accesseur vers la propriété 'City'
	 */
	public VAccessor<City> city() {
		return citIdAccessor;
	}

	/**
	 * Association : Attachment.
	 * @return l'accesseur vers la propriété 'Attachment'
	 */
	public ListVAccessor<Attachment> attachment() {
		return attachmentAccessor;
	}

	/**
	 * Association : Command type.
	 * @return l'accesseur vers la propriété 'Command type'
	 */
	public VAccessor<CommandType> commandType() {
		return ctyIdAccessor;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}

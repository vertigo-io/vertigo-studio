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
package io.vertigo.studio.domain.stereotype;

import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.structure.model.Entity;
import io.vertigo.datamodel.structure.model.UID;
import io.vertigo.datastore.impl.entitystore.StoreVAccessor;
import io.vertigo.datamodel.structure.stereotype.Field;
import io.vertigo.datamodel.structure.util.DtObjectUtil;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class CommandValidation implements Entity {
	private static final long serialVersionUID = 1L;

	private Long cvaId;
	private String signerName;

	@io.vertigo.datamodel.structure.stereotype.Association(
			name = "ACmdCva",
			fkFieldName = "cmdId",
			primaryDtDefinitionName = "DtCommand",
			primaryIsNavigable = true,
			primaryRole = "Command",
			primaryLabel = "Command",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DtCommandValidation",
			foreignIsNavigable = false,
			foreignRole = "CommandValidation",
			foreignLabel = "Command validation",
			foreignMultiplicity = "0..*")
	private final StoreVAccessor<io.vertigo.studio.domain.stereotype.Command> cmdIdAccessor = new StoreVAccessor<>(io.vertigo.studio.domain.stereotype.Command.class, "Command");

	/** {@inheritDoc} */
	@Override
	public UID<CommandValidation> getUID() {
		return UID.of(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'id'.
	 * @return Long cvaId <b>Obligatoire</b>
	 */
	@Field(smartType = "STyId", type = "ID", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "id")
	public Long getCvaId() {
		return cvaId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'id'.
	 * @param cvaId Long <b>Obligatoire</b>
	 */
	public void setCvaId(final Long cvaId) {
		this.cvaId = cvaId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Signer name'.
	 * @return String signerName <b>Obligatoire</b>
	 */
	@Field(smartType = "STyFullText", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "Signer name")
	public String getSignerName() {
		return signerName;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Signer name'.
	 * @param signerName String <b>Obligatoire</b>
	 */
	public void setSignerName(final String signerName) {
		this.signerName = signerName;
	}
	
	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Command'.
	 * @return Long cmdId
	 */
	@io.vertigo.datamodel.structure.stereotype.ForeignKey(smartType = "STyId", label = "Command", fkDefinition = "DtCommand" )
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
	public StoreVAccessor<io.vertigo.studio.domain.stereotype.Command> command() {
		return cmdIdAccessor;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}

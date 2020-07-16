package io.vertigo.studio.domain.stereotype;

import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.structure.model.KeyConcept;
import io.vertigo.datamodel.structure.model.UID;
import io.vertigo.datamodel.structure.stereotype.Field;
import io.vertigo.datamodel.structure.util.DtObjectUtil;
import io.vertigo.datastore.impl.entitystore.EnumStoreVAccessor;
import io.vertigo.datastore.impl.entitystore.StoreListVAccessor;
import io.vertigo.datastore.impl.entitystore.StoreVAccessor;

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
	private final EnumStoreVAccessor<io.vertigo.studio.domain.stereotype.CommandType, io.vertigo.studio.domain.stereotype.CommandTypeEnum> ctyIdAccessor = new EnumStoreVAccessor<>(io.vertigo.studio.domain.stereotype.CommandType.class, "CommandType", io.vertigo.studio.domain.stereotype.CommandTypeEnum.class);

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
	private final StoreVAccessor<io.vertigo.studio.domain.stereotype.City> citIdAccessor = new StoreVAccessor<>(io.vertigo.studio.domain.stereotype.City.class, "City");

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
	private final StoreListVAccessor<io.vertigo.studio.domain.stereotype.Attachment> attachmentAccessor = new StoreListVAccessor<>(this, "ACmdAtt", "Attachment");

	/** {@inheritDoc} */
	@Override
	public UID<Command> getUID() {
		return UID.of(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'id'.
	 * @return Long cmdId <b>Obligatoire</b>
	 */
	@Field(smartType = "STyId", type = "ID", cardinality = io.vertigo.core.lang.Cardinality.ONE, label = "id")
	public Long getCmdId() {
		return cmdId;
	}

	/**
	 * Champ : ID.
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
	@io.vertigo.datamodel.structure.stereotype.ForeignKey(smartType = "STyId", label = "Command type", fkDefinition = "DtCommandType")
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
	@io.vertigo.datamodel.structure.stereotype.ForeignKey(smartType = "STyId", label = "City", fkDefinition = "DtCity")
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
	public StoreVAccessor<io.vertigo.studio.domain.stereotype.City> city() {
		return citIdAccessor;
	}

	/**
	 * Association : Attachment.
	 * @return l'accesseur vers la propriété 'Attachment'
	 */
	public StoreListVAccessor<io.vertigo.studio.domain.stereotype.Attachment> attachment() {
		return attachmentAccessor;
	}

	/**
	 * Association : Command type.
	 * @return l'accesseur vers la propriété 'Command type'
	 */
	public EnumStoreVAccessor<io.vertigo.studio.domain.stereotype.CommandType, io.vertigo.studio.domain.stereotype.CommandTypeEnum> commandType() {
		return ctyIdAccessor;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}

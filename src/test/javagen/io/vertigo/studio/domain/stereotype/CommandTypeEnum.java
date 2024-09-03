package io.vertigo.studio.domain.stereotype;

import java.io.Serializable;

import io.vertigo.datamodel.data.model.MasterDataEnum;
import io.vertigo.datamodel.data.model.UID;

public enum CommandTypeEnum implements MasterDataEnum<io.vertigo.studio.domain.stereotype.CommandType> {

	;

	private final Serializable entityId;

	private CommandTypeEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public UID<io.vertigo.studio.domain.stereotype.CommandType> getEntityUID() {
		return UID.of(io.vertigo.studio.domain.stereotype.CommandType.class, entityId);
	}

}

package io.vertigo.vortex.model.library.builders;

import io.vertigo.vortex.model.library.types.VXDataType;

public final class VXBooleanBuilder extends VXAbstractDomainTypeBuilder<VXBooleanBuilder> {
	public VXBooleanBuilder(final String name) {
		super(name, VXDataType.Boolean);
	}
}

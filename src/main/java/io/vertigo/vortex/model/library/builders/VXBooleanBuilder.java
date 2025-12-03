package io.vertigo.vortex.model.library.builders;

import io.vertigo.vortex.model.types.VXDataType;

public final class VXBooleanBuilder extends VXAbstractDomainTypeBuilder<VXBooleanBuilder> {
	VXBooleanBuilder(final String name) {
		super(name, VXDataType.Boolean);
	}
}

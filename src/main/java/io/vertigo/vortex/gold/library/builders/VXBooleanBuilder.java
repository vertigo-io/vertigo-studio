package io.vertigo.vortex.gold.library.builders;

import io.vertigo.vortex.gold.library.types.VXDataType;

public final class VXBooleanBuilder extends VXAbstractDomainTypeBuilder<VXBooleanBuilder> {
	public VXBooleanBuilder(final String name) {
		super(name, VXDataType.Boolean);
	}
}

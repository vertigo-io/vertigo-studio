package io.vertigo.vortex.gold.library.builders;

import io.vertigo.vortex.gold.library.types.VXDataType;

public final class VXEntityBuilder extends VXAbstractDomainTypeBuilder<VXEntityBuilder> {
	public VXEntityBuilder(final String name) {
		super(name, VXDataType.Entity);
	}
}

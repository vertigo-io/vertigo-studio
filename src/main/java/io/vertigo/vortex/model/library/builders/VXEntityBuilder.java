package io.vertigo.vortex.model.library.builders;

import io.vertigo.vortex.model.library.types.VXDataType;

public final class VXEntityBuilder extends VXAbstractDomainTypeBuilder<VXEntityBuilder> {
	public VXEntityBuilder(final String name) {
		super(name, VXDataType.Entity);
	}
}

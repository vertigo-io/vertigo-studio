package io.vertigo.vortex.model.library.builders;

import io.vertigo.vortex.model.types.VXDataType;

public final class VXEntityBuilder extends VXAbstractDomainTypeBuilder<VXEntityBuilder> {
	VXEntityBuilder(final String name) {
		super(name, VXDataType.Entity);
	}
}

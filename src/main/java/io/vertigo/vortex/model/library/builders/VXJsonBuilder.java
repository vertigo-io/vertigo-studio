package io.vertigo.vortex.model.library.builders;

import io.vertigo.vortex.model.library.types.VXDataType;

public final class VXJsonBuilder extends VXAbstractDomainTypeBuilder<VXJsonBuilder> {
	public VXJsonBuilder(final String name) {
		super(name, VXDataType.Json);
	}
}

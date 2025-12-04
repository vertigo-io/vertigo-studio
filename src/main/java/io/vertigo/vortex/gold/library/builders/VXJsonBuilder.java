package io.vertigo.vortex.gold.library.builders;

import io.vertigo.vortex.gold.library.types.VXDataType;

public final class VXJsonBuilder extends VXAbstractDomainTypeBuilder<VXJsonBuilder> {
	public VXJsonBuilder(final String name) {
		super(name, VXDataType.Json);
	}
}

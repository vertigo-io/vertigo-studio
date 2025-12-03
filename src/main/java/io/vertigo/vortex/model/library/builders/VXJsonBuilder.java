package io.vertigo.vortex.model.library.builders;

import io.vertigo.vortex.model.types.VXDataType;

public final class VXJsonBuilder extends VXAbstractDomainTypeBuilder<VXJsonBuilder> {
	VXJsonBuilder(final String name) {
		super(name, VXDataType.Json);
	}
}

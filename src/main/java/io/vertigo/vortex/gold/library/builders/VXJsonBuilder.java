package io.vertigo.vortex.gold.library.builders;

import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDataType;

public final class VXJsonBuilder extends VXAbstractDomainTypeBuilder<VXJsonBuilder> {
	public VXJsonBuilder(final VXKey libraryKey, final String name) {
		super(libraryKey, name, VXDataType.Json);
	}
}

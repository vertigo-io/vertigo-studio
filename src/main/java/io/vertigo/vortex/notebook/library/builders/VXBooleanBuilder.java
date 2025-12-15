package io.vertigo.vortex.notebook.library.builders;

import io.vertigo.vortex.notebook.VXKey;
import io.vertigo.vortex.notebook.library.types.VXDataType;

public final class VXBooleanBuilder extends VXAbstractDomainTypeBuilder<VXBooleanBuilder> {
	public VXBooleanBuilder(final VXKey libraryKey, final String name) {
		super(libraryKey, name, VXDataType.Boolean);
	}
}

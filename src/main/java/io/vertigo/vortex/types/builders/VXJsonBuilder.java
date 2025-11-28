package io.vertigo.vortex.types.builders;

import io.vertigo.vortex.types.VXDataType;

public final class VXJsonBuilder extends AbstractVXDomainTypeBuilder<VXJsonBuilder> {
    public VXJsonBuilder(final String name) {
        super(name, VXDataType.Json);
    }
}

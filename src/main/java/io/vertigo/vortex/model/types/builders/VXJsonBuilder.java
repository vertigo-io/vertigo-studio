package io.vertigo.vortex.model.types.builders;

import io.vertigo.vortex.model.types.VXDataType;

public final class VXJsonBuilder extends AbstractVXDomainTypeBuilder<VXJsonBuilder> {
    public VXJsonBuilder(final String name) {
        super(name, VXDataType.Json);
    }
}

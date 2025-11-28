package io.vertigo.vortex.types.builders;

import io.vertigo.vortex.types.VXDataType;

public final class VXEntityBuilder extends AbstractVXDomainTypeBuilder<VXEntityBuilder> {
    public VXEntityBuilder(final String name) {
        super(name, VXDataType.Entity);
    }
}

package io.vertigo.vortex.model.types.builders;

import io.vertigo.vortex.model.types.VXDataType;

public final class VXEntityBuilder extends AbstractVXDomainTypeBuilder<VXEntityBuilder> {
    public VXEntityBuilder(final String name) {
        super(name, VXDataType.Entity);
    }
}

package io.vertigo.vortex.model.types;

import io.vertigo.vortex.model.AbstractVXDomainTypeBuilder;
import io.vertigo.vortex.model.VXDataType;

public final class VXEntityBuilder extends AbstractVXDomainTypeBuilder<VXEntityBuilder> {
    public VXEntityBuilder(final String name) {
        super(name, VXDataType.Entity);
    }
}

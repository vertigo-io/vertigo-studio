package io.vertigo.vortex.model.types;

import io.vertigo.vortex.model.AbstractVXDomainTypeBuilder;
import io.vertigo.vortex.model.VXDataType;

public final class VXJsonBuilder extends AbstractVXDomainTypeBuilder<VXJsonBuilder> {
    public VXJsonBuilder(final String name) {
        super(name, VXDataType.Json);
    }
}

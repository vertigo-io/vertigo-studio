package io.vertigo.vortex.model.types;

import io.vertigo.vortex.model.AbstractVXDomainTypeBuilder;
import io.vertigo.vortex.model.VXDataType;

public final class VXBooleanBuilder extends AbstractVXDomainTypeBuilder<VXBooleanBuilder> {
    public VXBooleanBuilder(final String name) {
        super(name, VXDataType.Boolean);
    }
}

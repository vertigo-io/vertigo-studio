package io.vertigo.vortex.types.builders;

import io.vertigo.vortex.types.VXDataType;

public final class VXBooleanBuilder extends AbstractVXDomainTypeBuilder<VXBooleanBuilder> {
    public VXBooleanBuilder(final String name) {
        super(name, VXDataType.Boolean);
    }
}

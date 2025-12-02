package io.vertigo.vortex.model.types.builders;

import io.vertigo.vortex.model.types.VXDataType;

public final class VXBooleanBuilder extends AbstractVXDomainTypeBuilder<VXBooleanBuilder> {
    public VXBooleanBuilder(final String name) {
        super(name, VXDataType.Boolean);
    }
}

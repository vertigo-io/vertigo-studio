package io.vertigo.vortex.model;

public final class VXJsonBuilder extends AbstractVXDomainTypeBuilder<VXJsonBuilder> {
    public VXJsonBuilder(final String name) {
        super(name, VXDataType.Json);
    }
}

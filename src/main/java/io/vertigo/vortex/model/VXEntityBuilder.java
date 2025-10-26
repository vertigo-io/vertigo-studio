package io.vertigo.vortex.model;

public final class VXEntityBuilder extends AbstractVXDomainTypeBuilder<VXEntityBuilder> {
    public VXEntityBuilder(final String name) {
        super(name, VXDataType.Entity);
    }
}

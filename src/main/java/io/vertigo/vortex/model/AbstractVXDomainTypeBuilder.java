package io.vertigo.vortex.model;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;

public abstract class AbstractVXDomainTypeBuilder<B extends AbstractVXDomainTypeBuilder<B>> implements Builder<VXDomainType> {
    protected final String _name;
    protected final VXDataType _dataType;
    protected final List<VXValidator> _validators = new ArrayList<>();

    protected AbstractVXDomainTypeBuilder(final String name, final VXDataType dataType) {
        _name = name;
        _dataType = dataType;
    }

    protected B self() {
        return (B) this;
    }

    @Override
    public VXDomainType build() {
        return new VXDomainType(_name, _dataType.name(), _validators);
    }
}

package io.vertigo.vortex.model;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;
import io.vertigo.vortex.model.validators.VXMaxLengthValidator;
import io.vertigo.vortex.model.validators.VXMinLengthValidator;
import io.vertigo.vortex.model.validators.VXPatternValidator;

public class VXDomainTypeBuilder implements Builder<VXDomainType> {
    private final String _name;
    private final VXDataType _dataType;
    private final List<VXValidator> _validators = new ArrayList<>();

    public VXDomainTypeBuilder(final String name, final VXDataType dataType) {
        _name = name;
        _dataType = dataType;
    }

    public VXDomainTypeBuilder minLength(final int min) {
        _validators.add(new VXMinLengthValidator(min));
        return this;
    }

    public VXDomainTypeBuilder maxLength(final int max) {
        _validators.add(new VXMaxLengthValidator(max));
        return this;
    }

    public VXDomainTypeBuilder pattern(final String pattern) {
        _validators.add(new VXPatternValidator(pattern));
        return this;
    }

    @Override
    public VXDomainType build() {
        return new VXDomainType(_name, _dataType.name(), _validators);
    }
}

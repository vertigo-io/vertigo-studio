package io.vertigo.vortex.model.types;

import io.vertigo.vortex.model.AbstractVXDomainTypeBuilder;
import io.vertigo.vortex.model.VXDataType;
import io.vertigo.vortex.model.validators.VXMaxValidator;
import io.vertigo.vortex.model.validators.VXMinValidator;

public final class VXNumberBuilder extends AbstractVXDomainTypeBuilder<VXNumberBuilder> {
    public VXNumberBuilder(final String name, final VXDataType dataType) {
        super(name, dataType);
    }

    public VXNumberBuilder min(final Number min) {
        _validators.add(new VXMinValidator(min));
        return self();
    }

    public VXNumberBuilder max(final Number max) {
        _validators.add(new VXMaxValidator(max));
        return self();
    }
}

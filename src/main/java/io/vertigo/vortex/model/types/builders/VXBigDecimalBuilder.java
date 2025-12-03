package io.vertigo.vortex.model.types.builders;

import java.math.BigDecimal;

import io.vertigo.vortex.model.types.VXDataType;
import io.vertigo.vortex.model.types.validators.number.VXMaxValidator;
import io.vertigo.vortex.model.types.validators.number.VXMinValidator;

public final class VXBigDecimalBuilder extends AbstractVXDomainTypeBuilder<VXBigDecimalBuilder> {
    public VXBigDecimalBuilder(final String name) {
        super(name, VXDataType.BigDecimal);
    }

    public VXBigDecimalBuilder min(final BigDecimal min) {
        _validators.add(new VXMinValidator(min));
        return self();
    }

    public VXBigDecimalBuilder max(final BigDecimal max) {
        _validators.add(new VXMaxValidator(max));
        return self();
    }
}

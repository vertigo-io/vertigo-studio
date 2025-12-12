package io.vertigo.vortex.gold.library.builders;

import java.math.BigDecimal;

import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDataType;
import io.vertigo.vortex.gold.library.validators.number.VXMaxValidator;
import io.vertigo.vortex.gold.library.validators.number.VXMinValidator;

public final class VXBigDecimalBuilder extends VXAbstractDomainTypeBuilder<VXBigDecimalBuilder> {
	public VXBigDecimalBuilder(final VXKey libraryKey, final String name) {
		super(libraryKey, name, VXDataType.BigDecimal);
	}

	public VXBigDecimalBuilder min(final BigDecimal min) {
		return withValidator(new VXMinValidator(min));
	}

	public VXBigDecimalBuilder max(final BigDecimal max) {
		return withValidator(new VXMaxValidator(max));
	}
}

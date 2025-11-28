package io.vertigo.vortex.types.builders;

import io.vertigo.vortex.types.VXDataType;
import io.vertigo.vortex.types.validators.VXEmailValidator;
import io.vertigo.vortex.types.validators.VXMaxLengthValidator;
import io.vertigo.vortex.types.validators.VXMinLengthValidator;
import io.vertigo.vortex.types.validators.VXPatternValidator;

public final class VXStringBuilder extends AbstractVXDomainTypeBuilder<VXStringBuilder> {
    public VXStringBuilder(final String name) {
        super(name, VXDataType.String);
    }

    public VXStringBuilder minLength(final int min) {
        _validators.add(new VXMinLengthValidator(min));
        return self();
    }

    public VXStringBuilder maxLength(final int max) {
        _validators.add(new VXMaxLengthValidator(max));
        return self();
    }

    public VXStringBuilder pattern(final String pattern) {
        _validators.add(new VXPatternValidator(pattern));
        return self();
    }

    public VXStringBuilder email() {
        _validators.add(new VXEmailValidator());
        return self();
    }
}

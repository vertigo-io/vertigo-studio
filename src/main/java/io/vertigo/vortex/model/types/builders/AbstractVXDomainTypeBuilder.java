package io.vertigo.vortex.model.types.builders;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;
import io.vertigo.vortex.model.types.VXDataType;
import io.vertigo.vortex.model.types.VXDomainType;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.validators.VXRequiredValidator;

abstract class AbstractVXDomainTypeBuilder<B extends AbstractVXDomainTypeBuilder<B>> implements Builder<VXDomainType> {
	private final String _name;
	private final VXDataType _dataType;
	private final List<VXValidator> _validators = new ArrayList<>();
	private final List<VXProperty> _properties = new ArrayList<>();

	protected AbstractVXDomainTypeBuilder(final String name, final VXDataType dataType) {
		_name = name;
		_dataType = dataType;
	}

	protected final B self() {
		return (B) this;
	}

	protected final B withValidator(VXValidator validator) {
		_validators.add(validator);
		return self();
	}

	public B required() {
		return withValidator(new VXRequiredValidator());
	}

	@Override
	public final VXDomainType build() {
		return new VXDomainType(_name, _dataType, _validators, _properties);
	}
}

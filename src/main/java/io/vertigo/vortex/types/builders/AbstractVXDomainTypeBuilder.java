package io.vertigo.vortex.types.builders;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;
import io.vertigo.vortex.types.VXDataType;
import io.vertigo.vortex.types.VXDomainType;
import io.vertigo.vortex.types.VXValidator;
import io.vertigo.vortex.types.VxProperty;

abstract class AbstractVXDomainTypeBuilder<B extends AbstractVXDomainTypeBuilder<B>> implements Builder<VXDomainType> {
	protected final String _name;
	protected final VXDataType _dataType;
	protected final List<VXValidator> _validators = new ArrayList<>();
	protected final List<VxProperty> _properties = new ArrayList<>();

	protected AbstractVXDomainTypeBuilder(final String name, final VXDataType dataType) {
		_name = name;
		_dataType = dataType;
	}

	protected final B self() {
		return (B) this;
	}

	@Override
	public final VXDomainType build() {
		return new VXDomainType(_name, _dataType, _validators, _properties);
	}
}

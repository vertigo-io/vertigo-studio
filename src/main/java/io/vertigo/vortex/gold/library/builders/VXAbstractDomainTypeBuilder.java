package io.vertigo.vortex.gold.library.builders;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;
import io.vertigo.vortex.gold.library.types.VXDataType;
import io.vertigo.vortex.gold.library.types.VXDomainType;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

abstract class VXAbstractDomainTypeBuilder<B extends VXAbstractDomainTypeBuilder<B>> implements Builder<VXDomainType> {
	private final String _name;
	private String _description;
	private final VXDataType _dataType;
	private final List<VXValidator> _validators = new ArrayList<>();
	private final List<VXProperty> _properties = new ArrayList<>();

	protected VXAbstractDomainTypeBuilder(final String name, final VXDataType dataType) {
		_name = name;
		_dataType = dataType;
		_description = name; //default description is name
	}

	protected final B self() {
		return (B) this;
	}

	protected final B withDescription(final String description) {
		_description = description;
		return self();
	}

	protected final B withValidator(final VXValidator validator) {
		_validators.add(validator);
		return self();
	}

	@Override
	public final VXDomainType build() {
		return new VXDomainType(_name, _description, _dataType, _validators, _properties);
	}
}

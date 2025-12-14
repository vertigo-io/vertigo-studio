package io.vertigo.vortex.gold.library.builders;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.vortex.gold.VXElementType;
import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDataType;
import io.vertigo.vortex.gold.library.types.VXDomainType;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

abstract class VXAbstractDomainTypeBuilder<B extends VXAbstractDomainTypeBuilder<B>> implements Builder<VXDomainType> {
	private final VXKey _libraryKey;
	private final String _name;
	private String _description;
	private final VXDataType _dataType;
	private final List<VXValidator> _validators = new ArrayList<>();
	private final List<VXProperty> _properties = new ArrayList<>();

	protected VXAbstractDomainTypeBuilder(final VXKey libraryKey, final String name, final VXDataType dataType) {
		Assertion.check()
				.isNotNull(libraryKey)
				.isTrue(libraryKey.type() == VXElementType.LIBRARY, "Owner of a domainType must be a library");
		//---
		_libraryKey = libraryKey;
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
		final VXKey domainTypeKey = new VXKey(_libraryKey, VXElementType.DOMAIN_TYPE, _name);
		return new VXDomainType(domainTypeKey, _description, _dataType, _validators, _properties);
	}
}

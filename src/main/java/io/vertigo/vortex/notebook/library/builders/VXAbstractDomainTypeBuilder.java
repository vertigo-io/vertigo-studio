package io.vertigo.vortex.notebook.library.builders;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;
import io.vertigo.vortex.notebook.library.types.VXDataType;
import io.vertigo.vortex.notebook.library.types.VXDomainType;
import io.vertigo.vortex.notebook.library.types.VXProperty;
import io.vertigo.vortex.notebook.library.types.VXValidator;

abstract class VXAbstractDomainTypeBuilder<B extends VXAbstractDomainTypeBuilder<B>> implements Builder<VXDomainType> {
	private final VXKey _libraryKey;
	private final String _key;
	private String _comment;
	private final VXDataType _dataType;
	private final List<VXValidator> _validators = new ArrayList<>();
	private final List<VXProperty> _properties = new ArrayList<>();

	protected VXAbstractDomainTypeBuilder(
			final VXKey libraryKey,
			final String key,
			final VXDataType dataType) {
		Assertion.check()
				.isNotNull(libraryKey)
				.isTrue(libraryKey.type() == VXElementType.LIBRARY, "Owner of a domainType must be a library");
		//---
		_libraryKey = libraryKey;
		_key = key;
		_dataType = dataType;
	}

	protected final B self() {
		return (B) this;
	}

	protected final B withComment(final String comment) {
		_comment = comment;
		return self();
	}

	protected final B withValidator(final VXValidator validator) {
		_validators.add(validator);
		return self();
	}

	@Override
	public final VXDomainType build() {
		final VXKey domainTypeKey = new VXKey(_libraryKey,
				VXElementType.DOMAIN_TYPE, _key);
		return new VXDomainType(
				domainTypeKey,
				_comment,
				_dataType,
				_validators,
				_properties);
	}
}

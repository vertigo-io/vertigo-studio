package io.vertigo.vortex.gold.library;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXElementType;
import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDomainType;

/**
 * Represents the entire data model.
 * This model is composed of a header and a list of files.
 * @synthetic
 */
public record VXLibrary(
		VXKey key,
		String description,
		List<VXDomainType> domainTypes) {

	public VXLibrary {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.LIBRARY, "An library's key must be of type LIBRARY_TYPE")
				.isNotBlank(description)
				.isNotNull(domainTypes);
	}
}

package io.vertigo.vortex.notebook.library;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;
import io.vertigo.vortex.notebook.library.types.VXDomainType;

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
				.isTrue(key.type() == VXElementType.LIBRARY, "A library's key must be of type LIBRARY")
				.isNotBlank(description)
				.isNotNull(domainTypes);
	}
}

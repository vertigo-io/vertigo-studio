package io.vertigo.vortex.gold.library;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.library.types.VXDomainType;

/**
 * Represents the entire data model.
 * This model is composed of a header and a list of files.
 * @synthetic
 */
public record VXLibrary(
		String name,
		String description,
		List<VXDomainType> domainTypes) {

	public VXLibrary {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(description)
				.isNotNull(domainTypes);
	}
}

package io.vertigo.vortex.model.types;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.catalog.VXDomainTypes;

/**
 * Represents the entire data model.
 * This model is composed of a header and a list of files.
 * @synthetic
 */
public record VXLibrary(
		List<VXDomainTypes> domainTypes) {

	public VXLibrary {
		Assertion.check()
				.isNotNull(domainTypes);
	}

	@Override
	public String toString() {
		return "Library{domainTypes='%s'}"
				.formatted(domainTypes);
	}
}

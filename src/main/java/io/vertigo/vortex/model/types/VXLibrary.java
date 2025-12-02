package io.vertigo.vortex.model.types;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.model.types.builders.VXDomainTypes;

/**
 * Represents the entire data model.
 * This model is composed of a header and a list of files.
 * @synthetic
 */
public record VXLibrary(
		List<VXDomainTypes> domainTYpes) {

	public VXLibrary {
		Assertion.check()
				.isNotNull(domainTYpes);
	}

	@Override
	public String toString() {
		return "Library{domainTypes='%s'}"
				.formatted(domainTYpes);
	}
}

package io.vertigo.vortex.model.library;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.VXDomainTypes;

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
}

package io.vertigo.vortex.notebook.library;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXIdentification;
import io.vertigo.vortex.notebook.library.types.VXType;

/**
 * Represents the entire data model.
 * This model is composed of a header and a list of files.
 * @synthetic
 */
public record VXLibrary(
		VXIdentification identification,
		List<VXType> domainTypes) {

	public VXLibrary {
		Assertion.check()
				.isNotNull(identification)
				.isTrue(identification.key().type() == VXElementType.LIBRARY, "A library's key must be of type LIBRARY")
				.isNotNull(domainTypes);
	}
}

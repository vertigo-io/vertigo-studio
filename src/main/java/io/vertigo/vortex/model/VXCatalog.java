package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

/**
 * Represents the entire data model.
 * This model is composed of a header and a list of files.
 * @synthetic
 */
public record VXCatalog(
		VXHeader header,
		List<VXFile> files) {

	public VXCatalog {
		Assertion.check()
				.isNotNull(header)
				.isNotNull(files);
	}

	@Override
	public String toString() {
		return "Catalog{header='%s', files='%s'}".formatted(header, files);
	}
}

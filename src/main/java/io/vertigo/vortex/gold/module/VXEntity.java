package io.vertigo.vortex.gold.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;

/**
 * Represents a business entity.
 * An entity has a name and a list of attributes.
 * For example, a 'Book' entity could have attributes like 'title' and 'author'.
 * @synthetic
 */
public record VXEntity(
		String name,
		String description,
		List<VXAttribute> attributes,
		List<VXLink> links) {

	public VXEntity {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(description)
				.isNotNull(attributes)
				.isNotNull(links);
	}
}

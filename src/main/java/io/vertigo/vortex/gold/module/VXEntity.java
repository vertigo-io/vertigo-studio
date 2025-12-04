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
		List<VXAttribute> attributes) {

	public VXEntity {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(attributes);
	}
}

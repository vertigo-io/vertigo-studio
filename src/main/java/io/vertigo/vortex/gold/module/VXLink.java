package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;

public record VXLink(
		String name,
		String description,
		VXEntity targetEntity,
		VXCardinality cardinality) {

	public VXLink {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(description)
				.isNotNull(targetEntity)
				.isNotNull(cardinality);
	}
}

package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXKey;

public record VXLink(
		String name,
		String description,
		VXKey targetEntityKey,
		VXCardinality cardinality) {

	public VXLink {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(description)
				.isNotNull(targetEntityKey)
				.isNotNull(cardinality);
	}

}

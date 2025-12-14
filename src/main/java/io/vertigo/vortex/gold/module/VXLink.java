package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXElementType;
import io.vertigo.vortex.gold.VXKey;

public record VXLink(
		VXKey key,
		String description,
		VXKey targetEntityKey,
		VXCardinality cardinality) {

	public VXLink {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.LINK, "A link's key must be of type LINK")
				.isNotBlank(description)
				.isNotNull(targetEntityKey)
				.isNotNull(cardinality);
	}

}

package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXUID;

public record VXLink(
		String name,
		String description,
		VXUID targetEntityUid,
		VXCardinality cardinality) {

	public VXLink {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(description)
				.isNotNull(targetEntityUid)
				.isNotNull(cardinality);
	}

}

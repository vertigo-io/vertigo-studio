package io.vertigo.vortex.notebook.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;

public record VXLink(
		VXKey key,
		String description,
		VXKey targetEntityKey,
		VXCardinality cardinality,
		VXLinkStereotype stereotype) {

	public VXLink {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.LINK, "A link's key must be of type LINK")
				.isNotBlank(description)
				.isNotNull(targetEntityKey)
				.isNotNull(cardinality)
				.isNotNull(stereotype);
		// --- Constraint : partOf has 1..1 cardinality
		if (stereotype == VXLinkStereotype.PART_OF) {
			Assertion.check()
					.isTrue(cardinality == VXCardinality.ONE,
							"A PART_OF link must have a ONE (1) cardinality, but was '{0}'",
							cardinality);
		}
	}

}

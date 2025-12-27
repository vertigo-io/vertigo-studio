package io.vertigo.vortex.notebook.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;

public record VXLink(
		VXKey key,
		String label,
		String comment,
		VXKey targetEntityKey,
		VXLinkStereotype stereotype) {

	public VXLink {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.LINK, "A link's key must be of type LINK")
				.isNotBlank(label)
				.isNotNull(targetEntityKey)
				.isNotNull(stereotype);
	}

}

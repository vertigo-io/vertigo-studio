package io.vertigo.vortex.notebook;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record VXIdentification(
		VXKey key,
		String comment,
		List<String> tags) {

	public VXIdentification {
		Assertion.check()
				.isNotNull(key)
				.isNotNull(tags);
	}
}

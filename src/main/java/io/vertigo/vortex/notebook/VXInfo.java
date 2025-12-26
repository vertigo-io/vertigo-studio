package io.vertigo.vortex.notebook;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record VXInfo(
		VXKey key,
		String comment,
		List<String> tags) {

	public VXInfo {
		Assertion.check()
				.isNotNull(key)
				.isNotNull(tags);
	}
}

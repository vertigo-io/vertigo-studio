package io.vertigo.vortex.impl.notebook.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawIdentification(
		String key,
		String comment,
		List<String> tags) {

	public RawIdentification {
		Assertion.check()
				.isNotNull(key)
				.isNotBlank(comment) // Added check for comment
				.isNotNull(tags);
	}
}

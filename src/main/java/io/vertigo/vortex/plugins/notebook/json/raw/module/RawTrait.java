package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawTrait(
		String comment,
		Map<String, RawAttribute> attributes) {

	public RawTrait {
		Assertion.check()
				// comment may be null
				.isNotNull(attributes);
	}
}

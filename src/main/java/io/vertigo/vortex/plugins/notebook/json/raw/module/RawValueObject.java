package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawValueObject(
		String comment,
		Map<String, RawAttribute> attributes) {

	public RawValueObject {
		Assertion.check()
				//comment may be null
				.isNotNull(attributes);
	}
}

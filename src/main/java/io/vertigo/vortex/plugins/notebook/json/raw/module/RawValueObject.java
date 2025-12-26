package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawValueObject(
		String key,
		String comment,
		List<RawAttribute> attributes) {

	public RawValueObject {
		Assertion.check()
				.isNotBlank(key)
				//comment may be null
				.isNotNull(attributes);
	}
}

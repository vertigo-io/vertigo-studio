package io.vertigo.vortex.plugins.notebook.json.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawType(
		String key,
		String comment,
		String dataType) {

	public RawType {
		Assertion.check()
				.isNotBlank(key)
				.isNotNull(dataType);
	}
}

package io.vertigo.vortex.plugins.notebook.json.raw.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawDomainType(
		String key,
		String comment,
		String dataType) {

	public RawDomainType {
		Assertion.check()
				.isNotBlank(key)
				.isNotNull(dataType);
	}
}

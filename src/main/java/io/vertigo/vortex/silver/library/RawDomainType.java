package io.vertigo.vortex.silver.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawDomainType(
		String key,
		String description,
		String dataType) {

	public RawDomainType {
		Assertion.check()
				.isNotBlank(key)
				.isNotNull(dataType);
	}
}

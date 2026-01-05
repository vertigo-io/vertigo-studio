package io.vertigo.vortex.plugins.notebook.json.raw.library;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawDomainType(
		String comment,
		@JsonProperty("data_type") String dataType) {

	public RawDomainType {
		Assertion.check()
				.isNotNull(dataType);
	}
}

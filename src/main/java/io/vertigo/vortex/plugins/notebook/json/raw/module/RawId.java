package io.vertigo.vortex.plugins.notebook.json.raw.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawId(
		String key,
		String label,
		String comment,
		@JsonProperty("domain_type") String domainType) {

	public RawId {
		Assertion.check()
				.isNotBlank(key)
				.isNotBlank(label)
				// comment may be null
				.isNotNull(domainType);
	}
}

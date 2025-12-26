package io.vertigo.vortex.plugins.notebook.json.raw.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawAttribute(
		String key,
		String label,
		String comment,
		@JsonProperty("domain-type") String domainType,
		boolean required) {

	public RawAttribute {
		Assertion.check()
				.isNotBlank(key)
				// comment may be null
				.isNotBlank(label)
				.isNotNull(domainType);
	}

}

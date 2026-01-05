package io.vertigo.vortex.plugins.notebook.json.raw.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLink(
		String label,
		String comment,
		@JsonProperty("target_entity_key") String targetEntityKey,
		String stereotype) {

	public RawLink {
		Assertion.check()
				.isNotBlank(label)
				//comment may be null
				.isNotNull(targetEntityKey)
				.isNotNull(stereotype);
	}
}

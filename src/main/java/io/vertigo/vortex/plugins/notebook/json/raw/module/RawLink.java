package io.vertigo.vortex.plugins.notebook.json.raw.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLink(
		String label,
		String comment,
		@JsonProperty("target-entity-key") String targetEntityKey,
		boolean required,
		String stereotype) {
}

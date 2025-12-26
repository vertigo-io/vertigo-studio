package io.vertigo.vortex.plugins.notebook.json.raw.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawId(
		String key,
		String label,
		String comment,
		@JsonProperty("domain-type") String domainType) {
}

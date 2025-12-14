package io.vertigo.vortex.silver.module;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawId(
		String key,
		String description,
		String domainType) {
}


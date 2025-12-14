package io.vertigo.vortex.silver.module;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLink(
		String key,
		String description,
		String targetEntityName,
		String cardinality) {
}

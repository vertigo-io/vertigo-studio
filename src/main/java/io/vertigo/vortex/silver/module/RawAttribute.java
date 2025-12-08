package io.vertigo.vortex.silver.module;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawAttribute(
		String name,
		String description,
		String domainType,
		String role,
		String cardinality) {
}

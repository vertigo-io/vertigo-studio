package io.vertigo.vortex.raw;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawAttribute(
		String name,
		String domainType,
		String role,
		String cardinality) {
}

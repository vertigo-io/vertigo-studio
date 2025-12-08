package io.vertigo.vortex.silver.module;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.vortex.silver.RawElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLink(
		String name,
		String description,
		String targetEntityName,
		String cardinality) implements RawElement {
}

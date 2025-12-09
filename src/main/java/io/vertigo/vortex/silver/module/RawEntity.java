package io.vertigo.vortex.silver.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawEntity(
		String name,
		String description,
		RawId id,
		List<RawAttribute> attributes,
		List<RawLink> links) {
}

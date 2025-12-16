package io.vertigo.vortex.impl.notebook.raw.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawEntity(
		String key,
		String description,
		RawId id,
		List<RawAttribute> attributes,
		List<RawLink> partOf,
		List<RawLink> memberOf,
		List<RawLink> uses) {
}

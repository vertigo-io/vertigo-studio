package io.vertigo.vortex.plugins.notebook.json.module.copy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawEntity(
		String key,
		String comment,
		RawId id,
		List<RawAttribute> attributes,
		List<RawLink> links) {
}

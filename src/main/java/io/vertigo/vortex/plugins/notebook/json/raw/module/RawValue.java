package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawValue(
		String key,
		String comment,
		List<RawAttribute> attributes) {
}

package io.vertigo.vortex.plugins.notebook.json.module.copy;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawAttribute(
		String key,
		String label,
		String comment,
		String type,
		boolean required) {
}

package io.vertigo.vortex.plugins.notebook.json.module;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLink(
		String key,
		String label,
		String comment,
		String targetEntityKey,
		boolean required,
		String stereotype) {
}

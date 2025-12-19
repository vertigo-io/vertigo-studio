package io.vertigo.vortex.plugins.notebook.json.raw.module.copy;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawId(
		String key,
		String label,
		String comment,
		String type) {
}




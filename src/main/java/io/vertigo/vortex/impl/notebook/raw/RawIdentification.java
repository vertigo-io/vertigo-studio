package io.vertigo.vortex.impl.notebook.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawIdentification(
		String key,
		String description,
		List<String> tags) {
}

package io.vertigo.vortex.impl.notebook.raw.module;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLink(
		String key,
		String description,
		String targetEntityKey,
		String cardinality) {
}

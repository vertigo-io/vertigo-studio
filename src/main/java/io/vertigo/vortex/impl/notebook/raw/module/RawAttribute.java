package io.vertigo.vortex.impl.notebook.raw.module;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawAttribute(
		String key,
		String label,
		String comment,
		String domainType,
		boolean required) {
}

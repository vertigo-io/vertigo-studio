package io.vertigo.vortex.silver;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawHeader(
		String description,
		String[] tags) {
}

package io.vertigo.vortex.raw;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawHeader(
		String description,
		String[] tags) {

	@Override
	public String toString() {
		return "Header{description='%s', tags='%s'}".formatted(description, tags);
	}
}

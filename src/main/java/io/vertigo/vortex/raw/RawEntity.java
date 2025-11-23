package io.vertigo.vortex.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawEntity(
		String name,
		List<RawAttribute> attributes) {

	@Override
	public String toString() {
		return "Entity{name='%s', attributes='%s'}".formatted(name, attributes);
	}
}

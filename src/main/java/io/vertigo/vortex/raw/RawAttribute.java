package io.vertigo.vortex.raw;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawAttribute(
		String name,
		String domainType,
		String role,
		String cardinality) {

	@Override
	public String toString() {
		return "Attribute{name='%s', domainType='%s', cardinality='%s'}".formatted(name, domainType, cardinality);
	}
}

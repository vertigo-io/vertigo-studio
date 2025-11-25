package io.vertigo.vortex.raw;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawDomainType(
		String name,
		String dataType) {

	@Override
	public String toString() {
		return "DomainType{name='%s', dataType='%s'}"
				.formatted(name, dataType);
	}
}

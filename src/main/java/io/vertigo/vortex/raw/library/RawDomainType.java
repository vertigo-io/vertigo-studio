package io.vertigo.vortex.raw.library;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawDomainType(
		String name,
		String dataType) {
}

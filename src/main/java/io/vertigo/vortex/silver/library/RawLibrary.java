package io.vertigo.vortex.silver.library;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLibrary(
		String name,
		String description,
		List<RawDomainType> domainTypes) {
}

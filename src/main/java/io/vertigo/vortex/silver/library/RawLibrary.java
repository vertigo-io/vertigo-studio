package io.vertigo.vortex.silver.library;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.vortex.silver.RawHeader;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLibrary(
		String library,
		RawHeader header,
		List<RawDomainType> domainTypes) {
}

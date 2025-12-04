package io.vertigo.vortex.raw.library;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.vortex.raw.RawHeader;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLibrarySource(
		String library,
		RawHeader header,
		List<RawDomainType> domainTypes) {
}

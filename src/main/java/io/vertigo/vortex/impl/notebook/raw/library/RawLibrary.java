package io.vertigo.vortex.impl.notebook.raw.library;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLibrary(
		String library,
		String description,
		List<RawDomainType> domainTypes) {

	public RawLibrary {
		Assertion.check()
				.isNotBlank(library)
				.isNotNull(domainTypes);
	}
}

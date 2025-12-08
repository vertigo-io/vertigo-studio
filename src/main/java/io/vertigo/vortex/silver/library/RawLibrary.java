package io.vertigo.vortex.silver.library;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.silver.RawElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLibrary(
		String name,
		String description,
		List<RawDomainType> domainTypes) implements RawElement {

	public RawLibrary {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(domainTypes);
	}
}

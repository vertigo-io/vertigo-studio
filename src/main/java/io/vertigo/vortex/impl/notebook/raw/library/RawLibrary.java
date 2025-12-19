package io.vertigo.vortex.impl.notebook.raw.library;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.impl.notebook.raw.RawIdentification;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLibrary(
		RawIdentification library,
		List<RawType> types) {

	public RawLibrary {
		Assertion.check()
				.isNotNull(library)
				.isNotNull(types);
	}
}

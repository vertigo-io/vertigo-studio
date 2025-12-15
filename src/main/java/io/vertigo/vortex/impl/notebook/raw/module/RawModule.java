package io.vertigo.vortex.impl.notebook.raw.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.impl.notebook.raw.RawIdentification;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModule(
		RawIdentification module,
		RawUses uses,
		List<RawEntity> entities) {

	public RawModule {
		Assertion.check()
				.isNotNull(module)
				.isNotNull(uses)
				.isNotNull(entities);
	}
}

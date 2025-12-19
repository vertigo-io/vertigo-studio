package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.plugins.notebook.json.raw.RawIdentification;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModule(
		RawIdentification module,
		RawImports imports,
		List<RawEntity> entities,
		List<RawValue> values) {

	public RawModule {
		Assertion.check()
				.isNotNull(module)
				.isNotNull(imports)
				.isNotNull(entities)
				.isNotNull(values);
	}
}

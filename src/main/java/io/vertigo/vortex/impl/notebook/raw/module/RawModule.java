package io.vertigo.vortex.impl.notebook.raw.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModule(
		String module,
		String description,
		RawUses uses,
		List<RawEntity> entities) {
}

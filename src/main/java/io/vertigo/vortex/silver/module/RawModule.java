package io.vertigo.vortex.silver.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModule(
		String module,
		String description,
		List<RawEntity> entities) {
}

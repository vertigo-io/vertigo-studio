package io.vertigo.vortex.silver.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModule(
		String name,
		String description,
		List<String> uses, //libraries
		List<String> imports, //modules
		List<RawEntity> entities) {
}

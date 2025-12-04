package io.vertigo.vortex.silver.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.vortex.silver.RawHeader;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModule(
		String module,
		RawHeader header,
		List<RawEntity> entities) {
}

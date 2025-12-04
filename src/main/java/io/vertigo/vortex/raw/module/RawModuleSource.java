package io.vertigo.vortex.raw.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.vortex.raw.RawHeader;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModuleSource(
		String module,
		RawHeader header,
		List<RawEntity> entities) {
}

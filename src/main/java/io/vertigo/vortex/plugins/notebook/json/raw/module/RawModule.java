package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.plugins.notebook.json.raw.RawInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModule(
		@JsonProperty("module_info") RawInfo moduleInfo,
		RawImports imports,
		Map<String, RawEntity> entities,
		@JsonProperty("value_objects") Map<String, RawValueObject> valueObjects,
		Map<String, RawTrait> traits) {

	public RawModule {
		Assertion.check()
				.isNotNull(moduleInfo)
				.isNotNull(imports)
				.isNotNull(entities)
				.isNotNull(valueObjects)
				.isNotNull(traits);
	}
}

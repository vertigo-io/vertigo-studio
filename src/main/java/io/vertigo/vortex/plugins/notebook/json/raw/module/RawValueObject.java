package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawValueObject(
		@JsonProperty("_comment") String _comment,
		Map<String, RawAttribute> attributes) {

	public RawValueObject {
		attributes = attributes != null
				? Collections.unmodifiableMap(new LinkedHashMap<>(attributes))
				: Map.of();

		// Optionnel : interdiction des clés commençant par _
		Assertion.check().isTrue(
				attributes.keySet().stream().noneMatch(k -> k.startsWith("_")),
				"Attribute keys must not start with '_' (reserved for internal use)");
	}

	@JsonAnySetter
	private void setAttribute(String key, RawAttribute value) {
		if (attributes instanceof LinkedHashMap<String, RawAttribute> map) {
			map.put(key, value);
		}
	}

	@JsonAnyGetter
	public Map<String, RawAttribute> attributes() {
		return attributes;
	}
}

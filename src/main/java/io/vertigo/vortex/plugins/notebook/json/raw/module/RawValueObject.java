package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawValueObject(
		@JsonProperty("_comment") String _comment,
		Map<String, RawAttribute> attributes) {

	public RawValueObject {
		attributes = new LinkedHashMap<>();
	}

	@JsonAnySetter
	private void setAttribute(String key, RawAttribute value) {
		attributes.put(key, value);
	}

	@JsonAnyGetter
	public Map<String, RawAttribute> attributes() {
		return attributes;
	}
}

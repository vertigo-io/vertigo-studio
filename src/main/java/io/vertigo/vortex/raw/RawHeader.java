package io.vertigo.vortex.raw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RawHeader {
	@JsonProperty("description")
	public String description;

	@JsonProperty("tags")
	public String[] tags;

	@Override
	public String toString() {
		return "Header{description='%s', tags='%s'}".formatted(description, tags);
	}
}

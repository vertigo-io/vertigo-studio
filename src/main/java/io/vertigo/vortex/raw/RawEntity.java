package io.vertigo.vortex.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RawEntity {
	@JsonProperty("name")
	public String name;
	@JsonProperty("attributes")
	public List<RawAttribute> rawAttributes;

	@Override
	public String toString() {
		return "Entity{name='%s', attributes='%s'}".formatted(name, rawAttributes);
	}
}

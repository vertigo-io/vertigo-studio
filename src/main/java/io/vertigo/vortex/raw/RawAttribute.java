package io.vertigo.vortex.raw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RawAttribute {
	@JsonProperty("name")
	public String name;
	@JsonProperty("domainType")
	public String domainType;
	@JsonProperty("role")
	public String role;
	@JsonProperty("cardinality")
	public String cardinality;

	@Override
	public String toString() {
		return "Attribute{name='%s', domainType='%s', cardinality='%s'}".formatted(name, domainType, cardinality);
	}
}

package io.vertigo.vortex.raw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RawDomainType {
	@JsonProperty("name")
	public String name;

	@JsonProperty("dataType")
	public String dataType;

	@Override
	public String toString() {
		return "DomainType{name='%s', dataType='%s'}".formatted(name, dataType);
	}
}

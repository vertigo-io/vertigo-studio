package io.vertigo.vortex.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RawModel {
	@JsonProperty("header")
	public RawHeader rawHeader;

	@JsonProperty("domainTypes")
	public List<RawDomainType> rawDomainTypes;

	@JsonProperty("entities")
	public List<RawEntity> rawEntities;

	@Override
	public String toString() {
		return "RawModel{header='%s', domainTypes='%s', entities='%s'}".formatted(rawHeader, rawDomainTypes, rawEntities);
	}
}

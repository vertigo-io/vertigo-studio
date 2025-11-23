package io.vertigo.vortex.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawModel(
		RawHeader header,
		List<RawDomainType> domainTypes,
		List<RawEntity> entities) {

	@Override
	public String toString() {
		return "RawModel{header='%s', domainTypes='%s', entities='%s'}".formatted(header, domainTypes, entities);
	}
}

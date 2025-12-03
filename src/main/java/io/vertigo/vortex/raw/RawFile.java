package io.vertigo.vortex.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawFile(
		String module,
		RawHeader header,
		List<RawDomainType> domainTypes,
		List<RawEntity> entities) {

	@Override
	public String toString() {
		return "RawFile{module='%s%', header='%s', domainTypes='%s', entities='%s'}"
				.formatted(module, header, domainTypes, entities);
	}
}

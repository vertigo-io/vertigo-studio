package io.vertigo.vortex.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawCatalog(
		RawHeader header,
		List<RawFile> files) {

	@Override
	public String toString() {
		return "RawCatalog{header='%s', files='%s'}".formatted(header, files);
	}
}

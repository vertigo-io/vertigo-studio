package io.vertigo.vortex.plugins.notebook.json.raw.library;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.plugins.notebook.json.raw.RawInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawLibrary(
		@JsonProperty("library-info") RawInfo libraryInfo,
		@JsonProperty("domain-types") Map<String, RawDomainType> domainTypes) {

	public RawLibrary {
		Assertion.check()
				.isNotNull(libraryInfo)
				.isNotNull(domainTypes);
	}
}


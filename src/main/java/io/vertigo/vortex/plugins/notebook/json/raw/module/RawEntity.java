package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawEntity(
		String comment,
		RawId id,
		Map<String, RawAttribute> attributes,
		Map<String, RawLink> links) {

	public RawEntity {
		Assertion.check()
				// comment may be null
				.isNotNull(id)
				.isNotNull(attributes)
				.isNotNull(links);
	}

}

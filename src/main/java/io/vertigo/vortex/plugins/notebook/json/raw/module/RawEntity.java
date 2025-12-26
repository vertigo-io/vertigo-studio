package io.vertigo.vortex.plugins.notebook.json.raw.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawEntity(
		String key,
		String comment,
		RawId id,
		List<RawAttribute> attributes,
		List<RawLink> links) {

	public RawEntity {
		Assertion.check()
				.isNotBlank(key)
				// comment may be null
				.isNotNull(id)
				.isNotNull(attributes)
				.isNotNull(links);
	}

}

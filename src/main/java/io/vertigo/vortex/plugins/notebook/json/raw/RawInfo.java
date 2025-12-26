package io.vertigo.vortex.plugins.notebook.json.raw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.core.lang.Assertion;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawInfo(
		String key,
		String comment,
		List<String> tags) {

	public RawInfo {
		Assertion.check()
				.isNotBlank(key)
				//comment may be null
				.isNotNull(tags);
	}
}

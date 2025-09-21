package io.vertigo.shiny.components.text.textpath;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTextPath(
		String path,
		String separator,
		@JsonIgnore ShinyTextPathStyle textPathStyle) implements ShinyComponent {

	public ShinyTextPath {
		Assertion.check().isNotBlank(path, "Path cannot be blank");
	}
}

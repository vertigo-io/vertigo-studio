package io.vertigo.shiny.components.text.textpath;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("textPath")
public record ShinyTextPath(
		String path,
		String separator) implements ShinyComponent {

	public ShinyTextPath {
		Assertion.check().isNotBlank(path, "Path cannot be blank");
	}
}

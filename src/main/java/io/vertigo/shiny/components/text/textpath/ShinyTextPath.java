package io.vertigo.shiny.components.text.textpath;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTextPath(
		String path,
		String separator) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyTextPath";
	}

	public ShinyTextPath {
		Assertion.check().isNotBlank(path, "Path cannot be blank");
	}
}

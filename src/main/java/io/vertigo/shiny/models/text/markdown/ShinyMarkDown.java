package io.vertigo.shiny.models.text.markdown;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyElement;
import io.vertigo.shiny.ShinyWriter;

public record ShinyMarkDown(
		String markdownText) implements ShinyElement {

	public ShinyMarkDown {
		Assertion.check().isNotNull(markdownText);
	}

	@Override
	public void render(final ShinyWriter writer) {
		new ShinyMarkDownRenderer().render(this, writer);
	}

}

package io.vertigo.shiny.models.text.markdown;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.ShinyWriter;

public record ShinyMarkDown(
		String markdownText) implements ShinyModel {

	public ShinyMarkDown {
		Assertion.check().isNotNull(markdownText);
	}

	@Override
	public void render(final ShinyWriter writer) {
		new ShinyMarkDownRenderer().render(this, writer);
	}

}

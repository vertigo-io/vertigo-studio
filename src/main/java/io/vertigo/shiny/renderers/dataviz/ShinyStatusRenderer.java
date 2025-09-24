package io.vertigo.shiny.renderers.dataviz;

import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.components.dataviz.status.ShinyStatus;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyStatusRenderer implements ShinyComponentRenderer<ShinyStatus> {
	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyStatus;
	}

	@Override
	public void render(final ShinyStatus shinyStatus) {
		Assertion.check()
				.isNotNull(shinyStatus);
		//---
		final ShinyStatusStyle style = Shiny.theme().statusStyle();
		final ShinyWriter writer = Shiny.writer();

		final String statusLine = shinyStatus.types().stream()
				.map(status -> status.color().fg(style.shape().getCharacter()))
				.collect(Collectors.joining(" "));

		writer.println(shinyStatus.title() != null ? shinyStatus.title() + " " + statusLine : statusLine);
	}
}

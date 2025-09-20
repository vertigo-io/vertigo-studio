package io.vertigo.shiny.components.dataviz.status;

import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;

public final class ShinyStatusRenderer {

	private ShinyStatusRenderer() {
		//private constructor
	}

	public static void render(final ShinyStatus shinyStatus, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyStatus);
		Assertion.check().isNotNull(writer);
		//---
		final String statusLine = shinyStatus.types().stream()
				.map(status -> status.color().fg(shinyStatus.style().shape().getCharacter()))
				.collect(Collectors.joining(" "));

		writer.println(shinyStatus.title() != null ? shinyStatus.title() + " " + statusLine : statusLine);
	}
}

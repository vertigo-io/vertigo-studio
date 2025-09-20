package io.vertigo.shiny.components.dataviz.status;

import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyComponentRenderer; // New import
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import

public final class ShinyStatusRenderer implements ShinyComponentRenderer<ShinyStatus> { // Implements interface

	public ShinyStatusRenderer() { // Public no-arg constructor
		//private constructor
	}

	@Override // Override annotation
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyStatus;
	}

	@Override // Override annotation
	public void render(final ShinyStatus shinyStatus, final ShinyWriter writer) { // Not static
		Assertion.check().isNotNull(shinyStatus);
		Assertion.check().isNotNull(writer);
		//---
		final String statusLine = shinyStatus.types().stream()
				.map(status -> status.color().fg(shinyStatus.style().shape().getCharacter()))
				.collect(Collectors.joining(" "));

		writer.println(shinyStatus.title() != null ? shinyStatus.title() + " " + statusLine : statusLine);
	}
}

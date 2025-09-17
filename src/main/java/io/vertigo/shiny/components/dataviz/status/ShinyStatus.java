package io.vertigo.shiny.components.dataviz.status;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyStatus implements ShinyComponent {
	private final String title;
	private final List<ShinyStatusType> statusTypes;
	private final ShinyStatusStyle statusStyle;

	// Package-private constructor, only accessible by the Builder
	ShinyStatus(ShinyStatusBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this.title = builder.title;
		this.statusTypes = builder.statusTypes;
		this.statusStyle = builder.statusStyle;
	}

	// Static factory method to get a new Builder instance
	public static ShinyStatusBuilder builder() {
		return new ShinyStatusBuilder();
	}

	public void render(final ShinyWriter writer) {
		final String statusLine = statusTypes.stream()
				.map(status -> status.color().fg(statusStyle.shape().getCharacter()))
				.collect(Collectors.joining(" "));

		writer.println(title != null ? title + " " + statusLine : statusLine);
	}
}

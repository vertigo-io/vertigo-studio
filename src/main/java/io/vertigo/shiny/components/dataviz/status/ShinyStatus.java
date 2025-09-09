package io.vertigo.shiny.components.dataviz.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyStatus implements ShinyComponent {
	private String title;
	private final List<ShinyStatusType> statusTypes = new ArrayList<>();
	private ShinyStatusStyle statusStyle;

	public ShinyStatus() {
		this.statusStyle = Shiny.theme().statusStyle();
	}

	public ShinyStatus style(final ShinyStatusStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.statusStyle = style;
		return this;
	}

	public ShinyStatus title(final String text) {
		this.title = text;
		return this;
	}

	public ShinyStatus types(final List<ShinyStatusType> types) {
		this.statusTypes.addAll(types);
		return this;
	}

	public ShinyStatus types(final ShinyStatusType... types) {
		return types(List.of(types));
	}

	public void render(final ShinyWriter writer) {
		final String statusLine = statusTypes.stream()
				.map(status -> status.color().fg(statusStyle.shape().getCharacter()))
				.collect(Collectors.joining(" "));

		writer.println(title != null ? title + " " + statusLine : statusLine);
	}
}

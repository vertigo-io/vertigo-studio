package io.vertigo.shiny.components.dataviz.status;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.color.ShinyColor;
import io.vertigo.shiny.color.ShinyColors;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyStatus implements ShinyComponent {
	private final Shiny shiny;
	private String title;
	private List<StatusType> statuses;
	private StatusShape shape = StatusShape.SQUARE;

	public ShinyStatus(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyStatus title(final String text) {
		this.title = text;
		return this;
	}

	public ShinyStatus statuses(final List<StatusType> statusList) {
		this.statuses = statusList;
		return this;
	}

	public ShinyStatus shape(final StatusShape statusShape) {
		this.shape = statusShape;
		return this;
	}

	public void print() {
		final String statusLine = statuses.stream()
				.map(status -> status.getColor() + shape.getCharacter() + ShinyColors.RESET)
				.collect(Collectors.joining(" "));

		shiny.getWriter().println(title != null ? title + " " + statusLine : statusLine);
	}

	public enum StatusType {
		SUCCESS(ShinyColors.GREEN),
		ERROR(ShinyColors.RED),
		WARNING(ShinyColors.YELLOW),
		INFO(ShinyColors.BLUE),
		NEUTRAL(ShinyColors.WHITE);

		private final ShinyColor color;

		StatusType(final ShinyColor color) {
			this.color = color;
		}

		public ShinyColor getColor() {
			return color;
		}
	}

	public enum StatusShape {
		SQUARE("■"),
		CIRCLE("●");

		private final String character;

		StatusShape(final String character) {
			this.character = character;
		}

		public String getCharacter() {
			return character;
		}
	}
}

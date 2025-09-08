package io.vertigo.shiny.components.dataviz.status;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyStatus implements ShinyComponent {
	private String title;
	private List<StatusType> statuses;
	private StatusShape shape = StatusShape.SQUARE;

	public ShinyStatus() {
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

	public void render(final ShinyWriter writer) {
		final String statusLine = statuses.stream()
				.map(status -> status.getColor().fg(shape.getCharacter()))
				.collect(Collectors.joining(" "));

		writer.println(title != null ? title + " " + statusLine : statusLine);
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

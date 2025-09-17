package io.vertigo.shiny.components.text.toggle;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;

public record ShinyToggle(
		String label,
		boolean value,
		ShinyToggleType type,
		String onText,
		String offText,
		boolean showText,
		ShinyToggleStyle style) implements ShinyComponent {

	public ShinyToggle {
	}

	// Static factory method to get a new Builder instance
	public static ShinyToggleBuilder builder() {
		return new ShinyToggleBuilder();
	}

	public void render(final ShinyWriter writer) {
		final String icon = value ? type.getOnIcon() : type.getOffIcon();
		final ShinyColor color = value
				? style.onColor()
				: style.offColor();
		final String text = showText ? (value ? onText : offText) : "";

		// Ajouter le toggleLabel si présent
		if (label != null && !label.isEmpty()) {
			writer.print(label)
					.print(": ");
		}

		// Construire la représentation du toggle
		writer.print(color.fg(icon
				+ ((showText && !text.isEmpty())
						? " " + text
						: "")))
				.println();
	}
}

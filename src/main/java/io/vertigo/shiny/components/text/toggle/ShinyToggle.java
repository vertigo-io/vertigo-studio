package io.vertigo.shiny.components.text.toggle;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;

public final class ShinyToggle implements ShinyComponent {
	private final String toggleLabel;
	private final boolean toggleValue;
	private final ShinyToggleType toggleType;
	private final String onText;
	private final String offText;
	private final boolean showText;
	private final ShinyToggleStyle toggleStyle;

	// Package-private constructor, only accessible by the Builder
	ShinyToggle(ShinyToggleBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this.toggleLabel = builder.toggleLabel;
		this.toggleValue = builder.toggleValue;
		this.toggleType = builder.toggleType;
		this.onText = builder.onText;
		this.offText = builder.offText;
		this.showText = builder.showText;
		this.toggleStyle = builder.toggleStyle;
	}

	// Static factory method to get a new Builder instance
	public static ShinyToggleBuilder builder() {
		return new ShinyToggleBuilder();
	}

	public void render(final ShinyWriter writer) {
		final String icon = toggleValue ? toggleType.getOnIcon() : toggleType.getOffIcon();
		final ShinyColor color = toggleValue
				? toggleStyle.onColor()
				: toggleStyle.offColor();
		final String text = showText ? (toggleValue ? onText : offText) : "";

		// Ajouter le toggleLabel si présent
		if (toggleLabel != null && !toggleLabel.isEmpty()) {
			writer.print(toggleLabel)
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

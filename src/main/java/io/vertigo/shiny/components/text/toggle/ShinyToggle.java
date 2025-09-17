package io.vertigo.shiny.components.text.toggle;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;

public final class ShinyToggle implements ShinyComponent {
	private String toggleLabel;
	private boolean toggleValue;
	private ShinyToggleType toggleType = ShinyToggleType.TOGGLE;
	private String onText = "ON";
	private String offText = "OFF";
	private boolean showText = true;
	private ShinyToggleStyle toggleStyle;

	public ShinyToggle() {
		toggleStyle = Shiny.theme().toggleStyle();
	}

	public ShinyToggle withStyle(final ShinyToggleStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.toggleStyle = style;
		return this;
	}

	public ShinyToggle withLabel(final String label) {
		this.toggleLabel = label;
		return this;
	}

	public ShinyToggle withValue(final boolean value) {
		this.toggleValue = value;
		return this;
	}

	public ShinyToggle withType(final ShinyToggleType type) {
		this.toggleType = type;
		return this;
	}

	public ShinyToggle withOnText(final String text) {
		this.onText = text;
		return this;
	}

	public ShinyToggle withOffText(final String text) {
		this.offText = text;
		return this;
	}

	public ShinyToggle withShowText(final boolean show) {
		this.showText = show;
		return this;
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
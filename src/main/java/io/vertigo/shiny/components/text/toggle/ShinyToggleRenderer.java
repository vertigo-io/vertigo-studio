package io.vertigo.shiny.components.text.toggle;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.renderers.ShinyComponentRenderer;
import io.vertigo.shiny.style.ShinyColor;

public final class ShinyToggleRenderer implements ShinyComponentRenderer<ShinyToggle> {

	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyToggle;
	}

	@Override
	public void render(final ShinyToggle shinyToggle, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyToggle);
		Assertion.check().isNotNull(writer);
		//---
		final String icon = shinyToggle.value() ? shinyToggle.type().getOnIcon() : shinyToggle.type().getOffIcon();
		final ShinyColor color = shinyToggle.value()
				? shinyToggle.style().onColor()
				: shinyToggle.style().offColor();
		final String text = shinyToggle.showText() ? (shinyToggle.value() ? shinyToggle.onText() : shinyToggle.offText()) : "";

		// Ajouter le toggleLabel si présent
		if (shinyToggle.label() != null && !shinyToggle.label().isEmpty()) {
			writer.print(shinyToggle.label())
					.print(": ");
		}

		// Construire la représentation du toggle
		writer.print(color.fg(icon
				+ ((shinyToggle.showText() && !text.isEmpty())
						? " " + text
						: "")))
				.println();
	}
}

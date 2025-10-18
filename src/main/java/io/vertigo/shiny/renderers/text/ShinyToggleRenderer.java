package io.vertigo.shiny.renderers.text;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.text.toggle.ShinyToggle;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;
import io.vertigo.shiny.style.ShinyColor;

public final class ShinyToggleRenderer implements ShinyComponentRenderer<ShinyToggle> {

	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyToggle;
	}

	@Override
	public void render(final ShinyToggle shinyToggle) {
		Assertion.check()
				.isNotNull(shinyToggle);
		//---
		final ShinyToggleStyle style = Shiny.theme().toggleStyle();
		final ShinyWriter writer = Shiny.writer();

		final String icon = shinyToggle.value() ? shinyToggle.toggleType().getOnIcon() : shinyToggle.toggleType().getOffIcon();
		final ShinyColor color = shinyToggle.value()
				? style.onColor()
				: style.offColor();
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

package io.vertigo.shiny.renderers.text;

import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.text.textpath.ShinyTextPath;
import io.vertigo.shiny.renderers.ShinyModelRenderer;
import io.vertigo.shiny.style.ShinyColor;

public final class ShinyTextPathRenderer implements ShinyModelRenderer<ShinyTextPath> {

	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyTextPath;
	}

	@Override
	public void render(final ShinyTextPath shinyTextPath) {
		Assertion.check()
				.isNotNull(shinyTextPath)
				.isNotBlank(shinyTextPath.path(), "Path cannot be blank");
		//---
		final ShinyTextPathStyle style = Shiny.theme().textPathStyle();
		final ShinyWriter writer = Shiny.writer();

		//		final StringBuilder coloredPath = new StringBuilder();
		final String[] parts = shinyTextPath.path().split(Pattern.quote(shinyTextPath.separator())); // Modified line

		final boolean relative = shinyTextPath.path().startsWith(shinyTextPath.separator());

		if (relative) {
			writer.print(style.rootColor().fg(shinyTextPath.separator()));
		}
		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			if (part.isEmpty()) { // Handle empty parts from leading/trailing/double separators
				continue;
			}

			final ShinyColor color;
			if (i == parts.length - 1) { // Leaf
				color = style.leafColor();
			} else if (!relative && i == 0) {// Root
				color = style.rootColor();
			} else { //node
				color = style.nodeColor();
			}
			writer.print(color.fg(part));

			if (i < parts.length - 1) {
				final ShinyColor sepColor;
				if (i == 0 && !relative && i == 0) {
					sepColor = style.rootColor();
				} else {
					sepColor = style.separatorColor();
				}
				writer.print(sepColor.fg(shinyTextPath.separator()));
			}
		}
		writer.println();
	}
}

package io.vertigo.shiny.components.text.textpath;

import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.style.ShinyColor;

public final class ShinyTextPathRenderer {

	private ShinyTextPathRenderer() {
		//private constructor
	}

	public static void render(final ShinyTextPath shinyTextPath, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyTextPath);
		Assertion.check().isNotNull(writer);
		Assertion.check().isNotBlank(shinyTextPath.path(), "Path cannot be blank");
		//---
		//		final StringBuilder coloredPath = new StringBuilder();
		final String[] parts = shinyTextPath.path().split(Pattern.quote(shinyTextPath.separator())); // Modified line

		final boolean relative = shinyTextPath.path().startsWith(shinyTextPath.separator());

		if (relative) {
			writer.print(shinyTextPath.textPathStyle().rootColor().fg(shinyTextPath.separator()));
		}
		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			if (part.isEmpty()) { // Handle empty parts from leading/trailing/double separators
				continue;
			}

			final ShinyColor color;
			if (i == parts.length - 1) { // Leaf
				color = shinyTextPath.textPathStyle().leafColor();
			} else if (!relative && i == 0) {// Root
				color = shinyTextPath.textPathStyle().rootColor();
			} else { //node
				color = shinyTextPath.textPathStyle().nodeColor();
			}
			writer.print(color.fg(part));

			if (i < parts.length - 1) {
				final ShinyColor sepColor;
				if (i == 0 && !relative && i == 0) {
					sepColor = shinyTextPath.textPathStyle().rootColor();
				} else {
					sepColor = shinyTextPath.textPathStyle().separatorColor();
				}
				writer.print(sepColor.fg(shinyTextPath.separator()));
			}
		}
		writer.println();
	}
}

package io.vertigo.shiny.components.text.textpath;

import java.util.regex.Pattern; // New import

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;

public final class ShinyTextPath implements ShinyComponent {
	private final String textPath;
	private final String separator;
	private final ShinyTextPathStyle textPathStyle;

	// Package-private constructor, only accessible by the Builder
	ShinyTextPath(ShinyTextPathBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this.textPath = builder.textPath;
		this.separator = builder.separator;
		this.textPathStyle = builder.textPathStyle;
	}

	// Static factory method to get a new Builder instance
	public static ShinyTextPathBuilder builder() {
		return new ShinyTextPathBuilder();
	}

	public void render(final ShinyWriter writer) {
		Assertion.check().isNotBlank(textPath, "Path cannot be blank");
		//---
		//		final StringBuilder coloredPath = new StringBuilder();
		final String[] parts = textPath.split(Pattern.quote(separator)); // Modified line

		final boolean relative = textPath.startsWith(separator);

		if (relative) {
			writer.print(textPathStyle.rootColor().fg(separator));
		}
		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			if (part.isEmpty()) { // Handle empty parts from leading/trailing/double separators
				continue;
			}

			final ShinyColor color;
			if (i == parts.length - 1) { // Leaf
				color = textPathStyle.leafColor();
			} else if (!relative && i == 0) {// Root
				color = textPathStyle.rootColor();
			} else { //node
				color = textPathStyle.nodeColor();
			}
			writer.print(color.fg(part));

			if (i < parts.length - 1) {
				final ShinyColor sepColor;
				if (i == 0 && !relative && i == 0) {
					sepColor = textPathStyle.rootColor();
				} else {
					sepColor = textPathStyle.separatorColor();
				}
				writer.print(sepColor.fg(separator));
			}
		}
		writer.println();
	}
}

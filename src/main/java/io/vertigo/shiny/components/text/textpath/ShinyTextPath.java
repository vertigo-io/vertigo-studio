package io.vertigo.shiny.components.text.textpath;

import java.nio.file.Path;
import java.util.regex.Pattern; // New import

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;

public final class ShinyTextPath implements ShinyComponent {
	private String textPath;
	private String separator = "/";
	private ShinyTextPathStyle textPathStyle;

	public ShinyTextPath() {
		textPathStyle = Shiny.theme().textPathStyle();
	}

	public ShinyTextPath style(final ShinyTextPathStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.textPathStyle = style;
		return this;
	}

	public ShinyTextPath path(final Path path) {
		this.textPath = path.normalize().toString();
		return this;
	}

	public ShinyTextPath path(final String path) {
		this.textPath = path;
		return this;
	}

	public ShinyTextPath separator(final String pathSeparator) {
		this.separator = pathSeparator;
		return this;
	}

	public void render(final ShinyWriter writer) {
		Assertion.check().isNotBlank(textPath, "Path cannot be blank");
		//---
		final StringBuilder coloredPath = new StringBuilder();
		final String[] parts = textPath.split(Pattern.quote(separator)); // Modified line

		final boolean relative = textPath.startsWith(separator);

		if (relative) {
			coloredPath.append(textPathStyle.rootColor().fg(separator));
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
			coloredPath.append(color.fg(part));

			if (i < parts.length - 1) {
				final ShinyColor sepColor;
				if (i == 0 && !relative && i == 0) {
					sepColor = textPathStyle.rootColor();
				} else {
					sepColor = textPathStyle.separatorColor();
				}
				coloredPath.append(sepColor.fg(separator));
			}
		}
		writer.println(coloredPath.toString());
	}
}

package io.vertigo.shell.shiny.textpath;

import java.util.regex.Pattern; // New import

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.ShinyComponent;
import io.vertigo.shell.shiny.utils.ShinyColor;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyTextPath implements ShinyComponent {
	private final Shiny shiny;
	private String path;
	private String separator = "/";
	private ShinyColor rootColor = ShinyColors.GREEN;
	private ShinyColor nodeColor = ShinyColors.YELLOW;
	private ShinyColor leafColor = ShinyColors.BLUE.bright();
	private ShinyColor separatorColor = ShinyColors.RED; // Default color

	public ShinyTextPath(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyTextPath path(final String textPath) {
		this.path = textPath;
		return this;
	}

	public ShinyTextPath separator(final String pathSeparator) {
		this.separator = pathSeparator;
		return this;
	}

	public ShinyTextPath rootColor(final ShinyColor color) {
		this.rootColor = color;
		return this;
	}

	public ShinyTextPath nodeColor(final ShinyColor color) {
		this.nodeColor = color;
		return this;
	}

	public ShinyTextPath leafColor(final ShinyColor color) {
		this.leafColor = color;
		return this;
	}

	public ShinyTextPath separatorColor(final ShinyColor color) {
		this.separatorColor = color;
		return this;
	}

	public void print() {
		Assertion.check().isNotBlank(path, "Path cannot be blank");
		//---
		final StringBuilder coloredPath = new StringBuilder();
		final String[] parts = path.split(Pattern.quote(separator)); // Modified line

		final boolean relative = path.startsWith(separator);

		if (relative) {
			coloredPath.append(rootColor).append(separator).append(ShinyColors.RESET);
		}
		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			if (part.isEmpty()) { // Handle empty parts from leading/trailing/double separators
				continue;
			}

			final ShinyColor color;
			if (i == parts.length - 1) { // Leaf
				color = leafColor;
			} else if (!relative && i == 0) {// Root
				color = rootColor;
			} else { //node
				color = nodeColor;
			}
			coloredPath.append(color).append(part).append(ShinyColors.RESET);

			if (i < parts.length - 1) {
				final ShinyColor sepColor;
				if (i == 0 && !relative && i == 0) {
					sepColor = rootColor;
				} else {
					sepColor = separatorColor;
				}
				coloredPath.append(sepColor).append(separator).append(ShinyColors.RESET);
			}
		}
		shiny.getWriter().println(coloredPath.toString());
	}
}

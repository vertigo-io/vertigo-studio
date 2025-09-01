package io.vertigo.shell.shiny.textpath;

import java.util.regex.Pattern; // New import

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyTextPath {
	private final Shiny shiny;
	private String path;
	private String separator = "/";
	private String rootColor = ShinyColors.GREEN;
	private String nodeColor = ShinyColors.YELLOW;
	private String leafColor = ShinyColors.BLUE_BRIGHT;
	private String separatorColor = ShinyColors.RED; // Default color

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

	public ShinyTextPath rootColor(final String color) {
		this.rootColor = color;
		return this;
	}

	public ShinyTextPath nodeColor(final String color) {
		this.nodeColor = color;
		return this;
	}

	public ShinyTextPath leafColor(final String color) {
		this.leafColor = color;
		return this;
	}

	public ShinyTextPath separatorColor(final String color) {
		this.separatorColor = color;
		return this;
	}

	public void print() {
		Assertion.check().isNotBlank(path, "Path cannot be blank");
		//---
		final StringBuilder coloredPath = new StringBuilder();
		final String[] parts = path.split(Pattern.quote(separator)); // Modified line

		if (path.startsWith(separator)) {
			coloredPath.append(rootColor).append(separator).append(ShinyColors.RESET);
		}

		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			if (part.isEmpty()) { // Handle empty parts from leading/trailing/double separators
				continue;
			}

			if (i == parts.length - 1) { // Leaf
				coloredPath.append(leafColor).append(part).append(ShinyColors.RESET);
			} else if (path.startsWith(separator) && i == 0) { // First part after root separator
				coloredPath.append(nodeColor).append(part).append(ShinyColors.RESET);
			} else {
				coloredPath.append(nodeColor).append(part).append(ShinyColors.RESET);
			}

			if (i < parts.length - 1) {
				coloredPath.append(separatorColor).append(separator).append(ShinyColors.RESET);
			}
		}
		shiny.getWriter().println(coloredPath.toString());
	}
}

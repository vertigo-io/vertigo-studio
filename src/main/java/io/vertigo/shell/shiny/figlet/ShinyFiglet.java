package io.vertigo.shell.shiny.figlet;

import java.io.IOException;

import com.github.dtmo.jfiglet.FigFontResources;
import com.github.dtmo.jfiglet.FigletRenderer;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColor;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyFiglet implements ShinyComponent {
	private final Shiny shiny;
	private String text;
	private ShinyFigletFonts font = ShinyFigletFonts.STANDARD; // Default font
	private ShinyColor figletColor = ShinyColors.BLUE;

	public ShinyFiglet(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyFiglet text(final String figletText) {
		this.text = figletText;
		return this;
	}

	public ShinyFiglet font(final ShinyFigletFonts figletFont) {
		this.font = figletFont;
		return this;
	}

	public ShinyFiglet color(final ShinyColor color) {
		this.figletColor = color;
		return this;
	}

	public void print() {
		try {
			final FigletRenderer figletRenderer = new FigletRenderer(FigFontResources.loadFigFontResource(font.getFileName()));
			final String asciiArt = figletRenderer.renderText(text);
			shiny.getWriter().print(figletColor);
			for (String line : asciiArt.split("\\r?\\n")) {
				shiny.getWriter().println(line);
			}
			shiny.getWriter().println(ShinyColors.RESET);
		} catch (final IOException e) {
			throw new RuntimeException("Failed to generate Figlet text", e);
		}
	}
}

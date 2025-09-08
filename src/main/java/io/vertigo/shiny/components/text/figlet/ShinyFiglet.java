package io.vertigo.shiny.components.text.figlet;

import java.io.IOException;

import com.github.dtmo.jfiglet.FigFontResources;
import com.github.dtmo.jfiglet.FigletRenderer;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

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
			final String asciiArt = figletColor.fg(figletRenderer.renderText(text));
			//We prefer use println instead of print a text with \n inside
			for (String line : asciiArt.split("\\r?\\n")) {
				shiny.getWriter().println(line);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Failed to generate Figlet text", e);
		}
	}
}

package io.vertigo.shiny.components.text.figlet;

import java.io.IOException;

import com.github.dtmo.jfiglet.FigFontResources;
import com.github.dtmo.jfiglet.FigletRenderer;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyFiglet implements ShinyComponent {
	private String figletText;
	private ShinyFigletFonts figletFont = ShinyFigletFonts.STANDARD; // Default figletFont
	private ShinyColor figletColor = ShinyColors.BLUE;

	public ShinyFiglet() {
	}

	public ShinyFiglet text(final String text) {
		this.figletText = text;
		return this;
	}

	public ShinyFiglet font(final ShinyFigletFonts font) {
		this.figletFont = font;
		return this;
	}

	public ShinyFiglet color(final ShinyColor color) {
		this.figletColor = color;
		return this;
	}

	public void render(final ShinyWriter writer) {
		try {
			final FigletRenderer figletRenderer = new FigletRenderer(FigFontResources.loadFigFontResource(figletFont.getFileName()));
			final String asciiArt = figletColor.fg(figletRenderer.renderText(figletText));
			//We prefer use println instead of print a figletText with \n inside
			for (String line : asciiArt.split("\\r?\\n")) {
				writer.println(line);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Failed to generate Figlet figletText", e);
		}
	}
}

package io.vertigo.shiny.components.text.figlet;

import java.io.IOException;

import com.github.dtmo.jfiglet.FigFontResources;
import com.github.dtmo.jfiglet.FigletRenderer;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyFiglet implements ShinyComponent {
	private String figletText;
	private ShinyFigletStyle figletStyle;

	public ShinyFiglet() {
		this.figletStyle = Shiny.theme().figletStyle();
	}

	public ShinyFiglet style(final ShinyFigletStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.figletStyle = style;
		return this;
	}

	public ShinyFiglet text(final String text) {
		this.figletText = text;
		return this;
	}

	public void render(final ShinyWriter writer) {
		try {
			final FigletRenderer figletRenderer = new FigletRenderer(FigFontResources.loadFigFontResource(figletStyle.figletFont.getFileName()));
			final String asciiArt = figletStyle.figletColor.fg(figletRenderer.renderText(figletText));
			//We prefer use println instead of print a figletText with \n inside
			for (String line : asciiArt.split("\\r?\\n")) {
				writer.println(line);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Failed to generate Figlet figletText", e);
		}
	}
}

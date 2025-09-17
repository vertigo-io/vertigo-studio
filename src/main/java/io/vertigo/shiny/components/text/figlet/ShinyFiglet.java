package io.vertigo.shiny.components.text.figlet;

import java.io.IOException;

import com.github.dtmo.jfiglet.FigFontResources;
import com.github.dtmo.jfiglet.FigletRenderer;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyFiglet(
		String text,
		ShinyFigletStyle style) implements ShinyComponent {

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}

	// Static factory method to get a new Builder instance
	public static ShinyFigletBuilder builder() {
		return new ShinyFigletBuilder();
	}

	public void render(final ShinyWriter writer) {
		try {
			final FigletRenderer figletRenderer = new FigletRenderer(FigFontResources.loadFigFontResource(style.font().getFileName()));
			final String asciiArt = style.color().fg(figletRenderer.renderText(text));
			//We prefer use println instead of print a figletText with \n inside
			for (String line : asciiArt.split("\\r?\\n")) {
				writer.println(line);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Failed to generate Figlet figletText", e);
		}
	}
}

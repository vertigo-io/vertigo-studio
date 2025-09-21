package io.vertigo.shiny.components.text.figlet;

import java.io.IOException;

import com.github.dtmo.jfiglet.FigFontResources;
import com.github.dtmo.jfiglet.FigletRenderer;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyComponentRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyFigletRenderer implements ShinyComponentRenderer<ShinyFiglet> {
	public void render(final ShinyFiglet shinyFiglet, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyFiglet);
		Assertion.check().isNotNull(writer);
		//---
		try {
			final FigletRenderer figletRenderer = new FigletRenderer(FigFontResources.loadFigFontResource(shinyFiglet.style().font().getFileName()));
			final String asciiArt = shinyFiglet.style().color().fg(figletRenderer.renderText(shinyFiglet.text()));
			//We prefer use println instead of print a figletText with \n inside
			for (String line : asciiArt.split("\\r?\\n")) {
				writer.println(line);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Failed to generate Figlet figletText", e);
		}
	}

	@Override
	public boolean accept(ShinyComponent component) {
		return component instanceof ShinyFiglet;
	}
}

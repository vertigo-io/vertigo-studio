package io.vertigo.shiny.renderers.text;

import java.io.IOException;

import com.github.dtmo.jfiglet.FigFontResources;
import com.github.dtmo.jfiglet.FigletRenderer;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.text.figlet.ShinyFiglet;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyFigletRenderer implements ShinyComponentRenderer<ShinyFiglet> {

	@Override
	public boolean accept(ShinyModel component) {
		return component instanceof ShinyFiglet;
	}

	public void render(final ShinyFiglet shinyFiglet) {
		Assertion.check()
				.isNotNull(shinyFiglet);
		//---
		final ShinyFigletStyle style = Shiny.theme().figletStyle();
		final ShinyWriter writer = Shiny.writer();

		try {
			final FigletRenderer figletRenderer = new FigletRenderer(FigFontResources.loadFigFontResource(style.font().getFileName()));
			final String asciiArt = style.color().fg(figletRenderer.renderText(shinyFiglet.text()));
			//We prefer use println instead of print a figletText with \n inside
			for (String line : asciiArt.split("\\r?\\n")) {
				writer.println(line);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Failed to generate Figlet figletText", e);
		}
	}

}

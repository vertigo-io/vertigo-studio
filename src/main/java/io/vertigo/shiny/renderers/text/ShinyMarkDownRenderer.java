package io.vertigo.shiny.renderers.text;

import java.util.Collections;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.text.markdown.ShinyMarkDown;
import io.vertigo.shiny.renderers.ShinyModelRenderer;

public final class ShinyMarkDownRenderer implements ShinyModelRenderer<ShinyMarkDown> {

	@Override
	public boolean accept(final ShinyModel model) {
		return model instanceof ShinyMarkDown;
	}

	@Override
	public void render(final ShinyMarkDown shinyMarkDown) {
		Assertion.check()
				.isNotNull(shinyMarkDown);
		//---
		final Parser parser = Parser.builder()
				.extensions(Collections.singletonList(TablesExtension.create()))
				.build();
		final Node document = parser.parse(shinyMarkDown.markdownText()); // Access directly
		document.accept(new ShinyMarkdownVisitor());
	}
}

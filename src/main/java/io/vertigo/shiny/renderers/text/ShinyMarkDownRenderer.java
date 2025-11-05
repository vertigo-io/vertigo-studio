package io.vertigo.shiny.renderers.text;

import java.util.Collections;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyRenderer; // New import
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyComponent;
import io.vertigo.shiny.models.text.markdown.ShinyMarkDown;

public final class ShinyMarkDownRenderer implements ShinyRenderer<ShinyMarkDown> {

	@Override 
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyMarkDown;
	}

	@Override 
	public void render(final ShinyMarkDown shinyMarkDown, final ShinyWriter writer) { 
		Assertion.check().isNotNull(shinyMarkDown);
		Assertion.check().isNotNull(writer);
		Assertion.check().isNotNull(shinyMarkDown.markdownText(), "Markdown text not set. Use fromFile() or fromText()."); // Access directly
		//---
		final Parser parser = Parser.builder()
				.extensions(Collections.singletonList(TablesExtension.create()))
				.build();
		final Node document = parser.parse(shinyMarkDown.markdownText()); // Access directly
		document.accept(new ShinyMarkdownVisitor(writer));
	}
}

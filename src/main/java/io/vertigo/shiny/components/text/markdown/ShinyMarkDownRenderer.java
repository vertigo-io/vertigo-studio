package io.vertigo.shiny.components.text.markdown;

import java.util.Collections;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyRenderer; // New import
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import

public final class ShinyMarkDownRenderer implements ShinyRenderer<ShinyMarkDown> { // Implements interface

	public ShinyMarkDownRenderer() { // Public no-arg constructor
		//private constructor
	}

	@Override // Override annotation
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyMarkDown;
	}

	@Override // Override annotation
	public void render(final ShinyMarkDown shinyMarkDown, final ShinyWriter writer) { // Not static
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

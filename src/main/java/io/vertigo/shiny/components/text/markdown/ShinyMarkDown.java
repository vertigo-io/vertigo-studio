package io.vertigo.shiny.components.text.markdown;

import java.util.Collections;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyMarkDown implements ShinyComponent {
	final String _markdownText;

	// Package-private constructor, only accessible by the Builder
	ShinyMarkDown(ShinyMarkDownBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this._markdownText = builder._markdownText;
	}

	// Static factory method to get a new Builder instance
	public static ShinyMarkDownBuilder builder() {
		return new ShinyMarkDownBuilder();
	}

	@Override
	public void render(final ShinyWriter writer) {
		Assertion.check().isNotNull(_markdownText, "Markdown text not set. Use fromFile() or fromText().");
		//---
		final Parser parser = Parser.builder()
				.extensions(Collections.singletonList(TablesExtension.create()))
				.build();
		final Node document = parser.parse(_markdownText);
		document.accept(new ShinyMarkdownVisitor(writer));
	}

}

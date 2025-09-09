package io.vertigo.shiny.components.text.markdown;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyMarkDown implements ShinyComponent {
	private String _markdownText;

	public ShinyMarkDown() {
	}

	public ShinyMarkDown fromFile(final String path) {
		try {
			this._markdownText = Files.readString(Path.of(path));
		} catch (final IOException e) {
			throw new RuntimeException("Error reading file: " + path, e);
		}
		return this;
	}

	public ShinyMarkDown fromText(final String text) {
		this._markdownText = text;
		return this;
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

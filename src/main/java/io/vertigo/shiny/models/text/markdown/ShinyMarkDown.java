package io.vertigo.shiny.components.text.markdown;

import java.util.Collections;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyComponent;

public record ShinyMarkDown(
		String markdownText) implements ShinyComponent {

	// Package-private constructor, only accessible by the Builder
	ShinyMarkDown(ShinyMarkDownBuilder builder) {
		this(builder.markdownText()); // Access builder field directly
	}

	// Static factory method to get a new Builder instance
	public static ShinyMarkDownBuilder builder() {
		return new ShinyMarkDownBuilder();
	}

	@Override
	public void render(final ShinyWriter writer) {
		new ShinyMarkDownRenderer().render(this, writer);
	}

}
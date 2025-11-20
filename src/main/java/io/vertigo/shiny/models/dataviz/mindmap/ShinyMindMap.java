package io.vertigo.shiny.models.dataviz.mindmap;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyMindMap(
		@Nonnull UUID id,
		@Nonnull String title,
		@Nonnull ShinyMindMapNode rootNode) implements ShinyBlock {

	public ShinyMindMap {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank")
				.isNotNull(rootNode, "Root node cannot be null");
	}
}

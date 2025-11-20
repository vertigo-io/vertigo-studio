package io.vertigo.shiny.models.data.tree;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyTree(
		@Nonnull UUID id,
		@Nonnull String label,
		@Nonnull List<ShinyTree> children) implements ShinyBlock {

	public ShinyTree {
		Assertion.check()
				.isNotNull(id)
				.isNotBlank(label)
				.isNotNull(children);
	}

	ShinyTree(final String label) {
		this(UUID.randomUUID(), label, List.of());
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}
}

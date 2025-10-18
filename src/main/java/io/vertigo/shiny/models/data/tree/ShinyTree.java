package io.vertigo.shiny.models.data.tree;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyTree(UUID id, String label, List<ShinyTree> children) implements ShinyModel {

	public ShinyTree {
		Assertion.check()
				.isNotNull(id)
				.isNotBlank(label)
				.isNotNull(children);
	}

	ShinyTree(String label) {
		this(UUID.randomUUID(), label, List.of());
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	@Override
	public String shinyType() {
		return "ShinyTree";
	}
}

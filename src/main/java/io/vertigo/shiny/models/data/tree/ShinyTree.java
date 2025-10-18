package io.vertigo.shiny.models.data.tree;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyTree(String label, List<ShinyTree> children) implements ShinyModel {

	public ShinyTree {
		Assertion.check()
				.isNotBlank(label)
				.isNotNull(children);
	}

	ShinyTree(String label) {
		this(label, List.of());
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	@Override
	public String shinyType() {
		return "ShinyTree";
	}
}

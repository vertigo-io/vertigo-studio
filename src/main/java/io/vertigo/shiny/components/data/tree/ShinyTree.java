package io.vertigo.shiny.components.data.tree;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTree(String label, List<ShinyTree> children) implements ShinyComponent {

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
	public String type() {
		return "tree";
	}
}

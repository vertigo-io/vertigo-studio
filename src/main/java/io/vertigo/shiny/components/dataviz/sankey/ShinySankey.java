package io.vertigo.shiny.components.dataviz.sankey;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinySankey(
		String title,
		List<ShinySankeyLink> data) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinySankey";
	}


	public ShinySankey {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank");
		Assertion.check()
				.isNotNull(data, "Data list cannot be null");
	}
}
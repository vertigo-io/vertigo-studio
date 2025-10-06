package io.vertigo.shiny.components.dataviz.sankey;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyType;
import io.vertigo.shiny.components.ShinyComponent;

@ShinyType("sankey")
public record ShinySankey(
		String title,
		List<ShinySankeyLink> data) implements ShinyComponent {

	public ShinySankey {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank");
		Assertion.check()
				.isNotNull(data, "Data list cannot be null");
	}
}
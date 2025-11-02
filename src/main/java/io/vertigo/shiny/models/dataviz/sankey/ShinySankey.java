package io.vertigo.shiny.models.dataviz.sankey;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinySankey(
		String title,
		List<ShinySankeyLink> data) implements ShinyBlock {

	public ShinySankey {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank")
				.isNotNull(data, "Data list cannot be null");
	}
}

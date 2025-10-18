package io.vertigo.shiny.models.dataviz.sankey;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinySankey(
		String title,
		List<ShinySankeyLink> data) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinySankey";
	}


	public ShinySankey {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank");
		Assertion.check()
				.isNotNull(data, "Data list cannot be null");
	}
}
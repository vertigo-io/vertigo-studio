package io.vertigo.shiny.models.dataviz.sankey;

public record ShinySankeyLink(
		String from,
		String to,
		double flow) {
}
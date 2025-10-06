package io.vertigo.shiny.components.dataviz.sankey;

public record ShinySankeyLink(
		String from,
		String to,
		double flow) {
}
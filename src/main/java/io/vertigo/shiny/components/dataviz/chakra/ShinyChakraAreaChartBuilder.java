package io.vertigo.shiny.components.dataviz.chakra;

import io.vertigo.core.lang.Assertion;

public final class ShinyChakraAreaChartBuilder {
	private String myTitle;
	private String[] myLabels;
	private int[] myData;

	public ShinyChakraAreaChartBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.myTitle = title;
		return this;
	}

	public ShinyChakraAreaChartBuilder withLabels(final String... labels) {
		Assertion.check().isNotNull(labels);
		//---
		this.myLabels = labels;
		return this;
	}

	public ShinyChakraAreaChartBuilder withData(final int... data) {
		Assertion.check().isNotNull(data);
		//---
		this.myData = data;
		return this;
	}

	public ShinyChakraAreaChart build() {
		return new ShinyChakraAreaChart(myTitle, myLabels, myData);
	}
}
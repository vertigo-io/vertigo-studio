package io.vertigo.shiny.components.dataviz.chakra;

import io.vertigo.core.lang.Assertion;

public final class ShinyChakraDonutChartBuilder {
	private String myTitle;
	private String[] myLabels;
	private int[] myData;

	public ShinyChakraDonutChartBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.myTitle = title;
		return this;
	}

	public ShinyChakraDonutChartBuilder withLabels(final String... labels) {
		Assertion.check().isNotNull(labels);
		//---
		this.myLabels = labels;
		return this;
	}

	public ShinyChakraDonutChartBuilder withData(final int... data) {
		Assertion.check().isNotNull(data);
		//---
		this.myData = data;
		return this;
	}

	public ShinyChakraDonutChart build() {
		return new ShinyChakraDonutChart(myTitle, myLabels, myData);
	}
}
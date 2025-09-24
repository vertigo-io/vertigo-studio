package io.vertigo.shiny.components.dataviz.chakra;

import io.vertigo.core.lang.Assertion;

public final class ShinyChakraPieChartBuilder {
	private String myTitle;
	private String[] myLabels;
	private int[] myData;

	public ShinyChakraPieChartBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.myTitle = title;
		return this;
	}

	public ShinyChakraPieChartBuilder withLabels(final String... labels) {
		Assertion.check().isNotNull(labels);
		//---
		this.myLabels = labels;
		return this;
	}

	public ShinyChakraPieChartBuilder withData(final int... data) {
		Assertion.check().isNotNull(data);
		//---
		this.myData = data;
		return this;
	}

	public ShinyChakraPieChart build() {
		return new ShinyChakraPieChart(myTitle, myLabels, myData);
	}
}
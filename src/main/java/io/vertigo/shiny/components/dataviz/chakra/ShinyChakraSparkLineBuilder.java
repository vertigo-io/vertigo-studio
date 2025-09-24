package io.vertigo.shiny.components.dataviz.chakra;

import io.vertigo.core.lang.Assertion;

public final class ShinyChakraSparkLineBuilder {
	private String myTitle;
	private int[] myData;

	public ShinyChakraSparkLineBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.myTitle = title;
		return this;
	}

	public ShinyChakraSparkLineBuilder withData(final int... data) {
		Assertion.check().isNotNull(data);
		//---
		this.myData = data;
		return this;
	}

	public ShinyChakraSparkLine build() {
		return new ShinyChakraSparkLine(myTitle, myData);
	}
}
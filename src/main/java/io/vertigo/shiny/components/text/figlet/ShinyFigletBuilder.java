package io.vertigo.shiny.components.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyFigletBuilder implements Builder<ShinyFiglet> {
	private String figletText;
	private ShinyFigletStyle figletStyle;

	public ShinyFigletBuilder() {
		this.figletStyle = Shiny.theme().figletStyle(); // Initialize default style
	}

	public ShinyFigletBuilder withStyle(final ShinyFigletStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.figletStyle = style;
		return this;
	}

	public ShinyFigletBuilder withText(final String text) {
		this.figletText = text;
		return this;
	}

	@Override
	public ShinyFiglet build() {
		return new ShinyFiglet(figletText, figletStyle);
	}
}

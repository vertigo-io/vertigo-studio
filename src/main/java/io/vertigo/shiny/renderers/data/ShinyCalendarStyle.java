package io.vertigo.shiny.renderers.data;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyRenderer;

public class ShinyCalendarStyle {
	private ShinyTableStyle tableStyle;

	public ShinyCalendarStyle withTableStyle(ShinyTableStyle style) {
		Assertion.check().isNotNull(style);
		this.tableStyle = style;
		return this;
	}

	public ShinyTableStyle tableStyle() {
		return tableStyle == null
				? ShinyRenderer.theme().tableStyle()
				: tableStyle;
	}

}

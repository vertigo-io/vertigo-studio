package io.vertigo.shiny.renderers.data;

public enum ShinyTableBorder {
	Normal,
	Horizontal,
	Simple,
	SimpleHeavy;

	private final ShinyTableBorderChars chars = new ShinyTableBorderChars(this);

	ShinyTableBorderChars chars() {
		return chars;
	}
}

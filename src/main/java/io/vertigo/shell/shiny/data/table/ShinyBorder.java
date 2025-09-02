package io.vertigo.shell.shiny.data.table;

public enum ShinyBorder {
	Normal,
	Horizontal,
	Simple,
	SimpleHeavy;

	private final ShinyBorderChars chars = new ShinyBorderChars(this);

	ShinyBorderChars chars() {
		return chars;
	}
}

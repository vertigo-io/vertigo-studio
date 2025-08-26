package io.vertigo.shell.shiny.table;

public enum ShinyBorder {
	Square,
	Horizontal,
	Simple,
	SimpleHeavy,
	Rounded,
	Ascii;

	private final ShinyBorderChars chars = new ShinyBorderChars(this);

	ShinyBorderChars chars() {
		return chars;
	}
}

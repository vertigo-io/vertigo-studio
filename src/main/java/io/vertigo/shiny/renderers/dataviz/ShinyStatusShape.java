package io.vertigo.shiny.renderers.dataviz;

public enum ShinyStatusShape {
	SQUARE("■"),
	CIRCLE("●");

	private final String character;

	ShinyStatusShape(final String character) {
		this.character = character;
	}

	String getCharacter() {
		return character;
	}
}

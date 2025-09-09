package io.vertigo.shiny.components.dataviz.status;

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

package io.vertigo.shiny.style;

public enum ShinyEffects {
	BOLD("\u001B[1m"),
	DIM("\u001B[2m"),
	ITALIC("\u001B[3m"), // souvent non supporté
	UNDERLINE("\u001B[4m"),
	BLINK("\u001B[5m"), // support variable
	INVERSE("\u001B[7m"),
	HIDDEN("\u001B[8m"), // texte masqué
	STRIKETHROUGH("\u001B[9m"); // souvent non supporté

	private final String effect;

	ShinyEffects(String effect) {
		this.effect = effect;
	}

	// Réinitialisation
	private static final String RESET = "\u001B[0m";

	// --- Methods to wrap text with effects ---
	public String apply(String s) {
		return effect + s + RESET;
	}
}

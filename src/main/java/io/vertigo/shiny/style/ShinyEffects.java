package io.vertigo.shiny.style;

public enum ShinyEffects {
	BOLD("\u001B[1m", ShinyReset.ALL), //BOLD_OFF),
	DIM("\u001B[2m", ShinyReset.NORMAL_INTENSITY),
	ITALIC("\u001B[3m", ShinyReset.ITALIC_OFF), // souvent non supporté
	UNDERLINE("\u001B[4m", ShinyReset.ALL), //UNDERLINE_OFF),
	BLINK("\u001B[5m", ShinyReset.BLINK_OFF), // support variable
	INVERSE("\u001B[7m", ShinyReset.INVERSE_OFF),
	HIDDEN("\u001B[8m", ShinyReset.REVEAL), // texte masqué
	STRIKETHROUGH("\u001B[9m", ShinyReset.STRIKETHROUGH_OFF); // souvent non supporté

	private final String effect;
	private final String effectReset;

	ShinyEffects(String effect, String effectReset) {
		this.effect = effect;
		this.effectReset = effectReset;
	}

	// --- Methods to wrap text with effects ---
	public String apply(String s) {
		return effect + s + effectReset;
	}
}

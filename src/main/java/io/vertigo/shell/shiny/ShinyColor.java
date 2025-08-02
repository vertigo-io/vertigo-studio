package io.vertigo.shell.shiny;

public enum ShinyColor {
	// Couleurs de police standard
	BLACK("\u001B[30m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	MAGENTA("\u001B[35m"),
	CYAN("\u001B[36m"),
	WHITE("\u001B[37m"),

	// Couleurs de police en gras (bold)
	BLACK_BOLD("\u001B[1;30m"),
	RED_BOLD("\u001B[1;31m"),
	GREEN_BOLD("\u001B[1;32m"),
	YELLOW_BOLD("\u001B[1;33m"),
	BLUE_BOLD("\u001B[1;34m"),
	MAGENTA_BOLD("\u001B[1;35m"),
	CYAN_BOLD("\u001B[1;36m"),
	WHITE_BOLD("\u001B[1;37m"),

	// Couleurs de police plus claires (bright)
	BLACK_BRIGHT("\u001B[90m"),
	RED_BRIGHT("\u001B[91m"),
	GREEN_BRIGHT("\u001B[92m"),
	YELLOW_BRIGHT("\u001B[93m"),
	BLUE_BRIGHT("\u001B[94m"),
	MAGENTA_BRIGHT("\u001B[95m"),
	CYAN_BRIGHT("\u001B[96m"),
	WHITE_BRIGHT("\u001B[97m"),

	// Couleurs d'arrière-plan standard
	BLACK_BG("\u001B[40m"),
	RED_BG("\u001B[41m"),
	GREEN_BG("\u001B[42m"),
	YELLOW_BG("\u001B[43m"),
	BLUE_BG("\u001B[44m"),
	MAGENTA_BG("\u001B[45m"),
	CYAN_BG("\u001B[46m"),
	WHITE_BG("\u001B[47m"),

	// Couleurs d'arrière-plan plus claires (bright)
	BLACK_BRIGHT_BG("\u001B[100m"),
	RED_BRIGHT_BG("\u001B[101m"),
	GREEN_BRIGHT_BG("\u001B[102m"),
	YELLOW_BRIGHT_BG("\u001B[103m"),
	BLUE_BRIGHT_BG("\u001B[104m"),
	MAGENTA_BRIGHT_BG("\u001B[105m"),
	CYAN_BRIGHT_BG("\u001B[106m"),
	WHITE_BRIGHT_BG("\u001B[107m"),

	// Styles supplémentaires
	UNDERLINE("\u001B[4m"),
	BLINK("\u001B[5m"), // Note : support limité sur certains terminaux
	INVERSE("\u001B[7m"),

	// Réinitialisation
	RESET("\u001B[0m");

	private final String code;

	private ShinyColor(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}
}

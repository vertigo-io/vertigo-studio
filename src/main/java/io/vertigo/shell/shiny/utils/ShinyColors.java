package io.vertigo.shell.shiny.utils;

public final class ShinyColors {

	// Couleurs de police standard
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String MAGENTA = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

	// Couleurs de police en gras (bold)
	public static final String BLACK_BOLD = "\u001B[1;30m";
	public static final String RED_BOLD = "\u001B[1;31m";
	public static final String GREEN_BOLD = "\u001B[1;32m";
	public static final String YELLOW_BOLD = "\u001B[1;33m";
	public static final String BLUE_BOLD = "\u001B[1;34m";
	public static final String MAGENTA_BOLD = "\u001B[1;35m";
	public static final String CYAN_BOLD = "\u001B[1;36m";
	public static final String WHITE_BOLD = "\u001B[1;37m";

	// Couleurs de police plus claires (bright)
	public static final String BLACK_BRIGHT = "\u001B[90m";
	public static final String RED_BRIGHT = "\u001B[91m";
	public static final String GREEN_BRIGHT = "\u001B[92m";
	public static final String YELLOW_BRIGHT = "\u001B[93m";
	public static final String BLUE_BRIGHT = "\u001B[94m";
	public static final String MAGENTA_BRIGHT = "\u001B[95m";
	public static final String CYAN_BRIGHT = "\u001B[96m";
	public static final String WHITE_BRIGHT = "\u001B[97m";

	// Couleurs d'arrière-plan standard
	public static final String BLACK_BG = "\u001B[40m";
	public static final String RED_BG = "\u001B[41m";
	public static final String GREEN_BG = "\u001B[42m";
	public static final String YELLOW_BG = "\u001B[43m";
	public static final String BLUE_BG = "\u001B[44m";
	public static final String MAGENTA_BG = "\u001B[45m";
	public static final String CYAN_BG = "\u001B[46m";
	public static final String WHITE_BG = "\u001B[47m";

	// Couleurs d'arrière-plan plus claires (bright)
	public static final String BLACK_BRIGHT_BG = "\u001B[100m";
	public static final String RED_BRIGHT_BG = "\u001B[101m";
	public static final String GREEN_BRIGHT_BG = "\u001B[102m";
	public static final String YELLOW_BRIGHT_BG = "\u001B[103m";
	public static final String BLUE_BRIGHT_BG = "\u001B[104m";
	public static final String MAGENTA_BRIGHT_BG = "\u001B[105m";
	public static final String CYAN_BRIGHT_BG = "\u001B[106m";
	public static final String WHITE_BRIGHT_BG = "\u001B[107m";

	// Styles supplémentaires
	public static final String UNDERLINE = "\u001B[4m";
	public static final String BLINK = "\u001B[5m"; // support variable
	public static final String INVERSE = "\u001B[7m";
	public static final String DIM = "\u001B[2m";

	// Réinitialisation
	public static final String RESET = "\u001B[0m";

	private ShinyColors() {
	}
}

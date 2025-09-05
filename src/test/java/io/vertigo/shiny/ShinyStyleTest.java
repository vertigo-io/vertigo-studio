package io.vertigo.shiny;

import io.vertigo.shiny.style.ShinyColors;
import io.vertigo.shiny.style.ShinyEffects;

public class ShinyStyleTest {

	public static void main(String[] args) {
		System.out.println(" no effect");
		System.out.println();
		testColors();
		testEffects();
		testCombos();
		testShake();
	}

	private static void testColors() {
		// --- Standard foreground
		System.out.print("fg        > ");
		System.out.print(ShinyColors.BLACK.fg(" black"));
		System.out.print(ShinyColors.RED.fg(" red"));
		System.out.print(ShinyColors.GREEN.fg(" green"));
		System.out.print(ShinyColors.YELLOW.fg(" yellow"));
		System.out.print(ShinyColors.BLUE.fg(" blue"));
		System.out.print(ShinyColors.MAGENTA.fg(" magenta"));
		System.out.print(ShinyColors.CYAN.fg(" cyan"));
		System.out.print(ShinyColors.WHITE.fg(" white"));
		System.out.println();
		System.out.println();

		// --- Bright foreground
		System.out.print("fg bright > ");
		System.out.print(ShinyColors.BLACK_BRIGHT.fg(" black"));
		System.out.print(ShinyColors.RED_BRIGHT.fg(" red"));
		System.out.print(ShinyColors.GREEN_BRIGHT.fg(" green"));
		System.out.print(ShinyColors.YELLOW_BRIGHT.fg(" yellow"));
		System.out.print(ShinyColors.BLUE_BRIGHT.fg(" blue"));
		System.out.print(ShinyColors.MAGENTA_BRIGHT.fg(" magenta"));
		System.out.print(ShinyColors.CYAN_BRIGHT.fg(" cyan"));
		System.out.print(ShinyColors.WHITE_BRIGHT.fg(" white"));
		System.out.println();
		System.out.println();

		// --- Standard background
		System.out.print("bg        > ");
		System.out.print(ShinyColors.BLACK.bg(" black"));
		System.out.print(ShinyColors.RED.bg(" red"));
		System.out.print(ShinyColors.GREEN.bg(" green"));
		System.out.print(ShinyColors.YELLOW.bg(" yellow"));
		System.out.print(ShinyColors.BLUE.bg(" blue"));
		System.out.print(ShinyColors.MAGENTA.bg(" magenta"));
		System.out.print(ShinyColors.CYAN.bg(" cyan"));
		System.out.print(ShinyColors.WHITE.bg(" white"));
		System.out.println();
		System.out.println();

		// --- Bright background
		System.out.print("bg bright > ");
		System.out.print(ShinyColors.BLACK_BRIGHT.bg(" black"));
		System.out.print(ShinyColors.RED_BRIGHT.bg(" red"));
		System.out.print(ShinyColors.GREEN_BRIGHT.bg(" green"));
		System.out.print(ShinyColors.YELLOW_BRIGHT.bg(" yellow"));
		System.out.print(ShinyColors.BLUE_BRIGHT.bg(" blue"));
		System.out.print(ShinyColors.MAGENTA_BRIGHT.bg(" magenta"));
		System.out.print(ShinyColors.CYAN_BRIGHT.bg(" cyan"));
		System.out.print(ShinyColors.WHITE_BRIGHT.bg(" white"));
		System.out.println();
		System.out.println();
	}

	private static void testEffects() {
		System.out.print("effects   >");
		System.out.print(ShinyEffects.BLINK.apply(" blink"));
		System.out.print(ShinyEffects.BOLD.apply(" bold"));
		System.out.print(ShinyEffects.DIM.apply(" dim"));
		System.out.print(ShinyEffects.HIDDEN.apply(" hidden"));
		System.out.print(ShinyEffects.INVERSE.apply(" inverse"));
		System.out.print(ShinyEffects.ITALIC.apply(" italic"));
		System.out.print(ShinyEffects.STRIKETHROUGH.apply(" strike through"));
		System.out.print(ShinyEffects.UNDERLINE.apply(" underline"));
		System.out.println();
		System.out.println();
	}

	private static void testCombos() {
		System.out.println("combos    >");

		// Color + effect
		System.out.println(ShinyEffects.BOLD.apply(ShinyColors.RED.fg(" bold red text")));
		System.out.println(ShinyEffects.UNDERLINE.apply(ShinyColors.BLUE.fg(" underlined blue text")));
		System.out.println(ShinyEffects.ITALIC.apply(ShinyColors.GREEN.fg(" italic green text")));

		// Foreground + background
		System.out.println(ShinyColors.YELLOW.fg(ShinyColors.BLUE.bg(" yellow on blue")));
		System.out.println(ShinyColors.CYAN_BRIGHT.fg(ShinyColors.RED.bg(" bright cyan on red")));

		// Multiple effects
		String combo = ShinyEffects.BOLD.apply(
				ShinyEffects.UNDERLINE.apply(
						ShinyColors.MAGENTA_BRIGHT.fg(" bold underlined bright magenta")));
		System.out.println(combo);

		System.out.println();
	}

	private static void testShake() {
		String text;
		// --- Standard foreground + background
		System.out.print("[fg= yellow, bg=red] ");
		text = ShinyColors.RED.bg(ShinyColors.YELLOW.fg("this is a beautiful text"));
		System.out.println(text);

		// --- Foreground + effect
		System.out.print("[fg= blue, effect=inverse] ");
		text = ShinyEffects.INVERSE.apply(ShinyColors.BLUE.fg("this is a beautiful text"));
		System.out.println(text);

		// --- Foreground + background + effect
		System.out.print("[fg= white bright, bg=green, effect=bold] ");
		text = ShinyEffects.BOLD.apply(ShinyColors.GREEN.bg(ShinyColors.WHITE_BRIGHT.fg("shiny combo")));
		System.out.println(text);

		System.out.println();
		System.out.println();
	}
}

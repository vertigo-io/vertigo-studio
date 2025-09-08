package io.vertigo.shiny;

import io.vertigo.shiny.style.ShinyColors;
import io.vertigo.shiny.style.ShinyEffects;

public class ShinyStyleTest {

	public static void main(String[] args) {
		final ShinyWriter writer = Shiny.writer();

		writer.println(" no effect");
		writer.println();
		testColors(writer);
		testEffects(writer);
		testCombos(writer);
		testShake(writer);
	}

	private static void testColors(final ShinyWriter writer) {
		// --- Standard foreground
		writer.print("fg        > ");
		writer.print(ShinyColors.BLACK.fg(" black"));
		writer.print(ShinyColors.RED.fg(" red"));
		writer.print(ShinyColors.GREEN.fg(" green"));
		writer.print(ShinyColors.YELLOW.fg(" yellow"));
		writer.print(ShinyColors.BLUE.fg(" blue"));
		writer.print(ShinyColors.MAGENTA.fg(" magenta"));
		writer.print(ShinyColors.CYAN.fg(" cyan"));
		writer.print(ShinyColors.WHITE.fg(" white"));
		writer.println();
		writer.println();

		// --- Bright foreground
		writer.print("fg bright > ");
		writer.print(ShinyColors.BLACK_BRIGHT.fg(" black"));
		writer.print(ShinyColors.RED_BRIGHT.fg(" red"));
		writer.print(ShinyColors.GREEN_BRIGHT.fg(" green"));
		writer.print(ShinyColors.YELLOW_BRIGHT.fg(" yellow"));
		writer.print(ShinyColors.BLUE_BRIGHT.fg(" blue"));
		writer.print(ShinyColors.MAGENTA_BRIGHT.fg(" magenta"));
		writer.print(ShinyColors.CYAN_BRIGHT.fg(" cyan"));
		writer.print(ShinyColors.WHITE_BRIGHT.fg(" white"));
		writer.println();
		writer.println();

		// --- Standard background
		writer.print("bg        > ");
		writer.print(ShinyColors.BLACK.bg(" black"));
		writer.print(ShinyColors.RED.bg(" red"));
		writer.print(ShinyColors.GREEN.bg(" green"));
		writer.print(ShinyColors.YELLOW.bg(" yellow"));
		writer.print(ShinyColors.BLUE.bg(" blue"));
		writer.print(ShinyColors.MAGENTA.bg(" magenta"));
		writer.print(ShinyColors.CYAN.bg(" cyan"));
		writer.print(ShinyColors.WHITE.bg(" white"));
		writer.println();
		writer.println();

		// --- Bright background
		writer.print("bg bright > ");
		writer.print(ShinyColors.BLACK_BRIGHT.bg(" black"));
		writer.print(ShinyColors.RED_BRIGHT.bg(" red"));
		writer.print(ShinyColors.GREEN_BRIGHT.bg(" green"));
		writer.print(ShinyColors.YELLOW_BRIGHT.bg(" yellow"));
		writer.print(ShinyColors.BLUE_BRIGHT.bg(" blue"));
		writer.print(ShinyColors.MAGENTA_BRIGHT.bg(" magenta"));
		writer.print(ShinyColors.CYAN_BRIGHT.bg(" cyan"));
		writer.print(ShinyColors.WHITE_BRIGHT.bg(" white"));
		writer.println();
		writer.println();
	}

	private static void testEffects(final ShinyWriter writer) {
		writer.print("effects   >");
		writer.println(ShinyEffects.BLINK.apply(" blink") + " no effect");
		writer.println(ShinyEffects.BOLD.apply(" bold") + " no effect");
		writer.println(ShinyEffects.DIM.apply(" dim") + " no effect");
		writer.println(ShinyEffects.HIDDEN.apply(" hidden") + " no effect");
		writer.println(ShinyEffects.INVERSE.apply(" inverse") + " no effect");
		writer.println(ShinyEffects.ITALIC.apply(" italic") + " no effect");
		writer.println(ShinyEffects.STRIKETHROUGH.apply(" strike through") + " no effect");
		writer.println(ShinyEffects.UNDERLINE.apply(" underline") + " no effect");
		writer.println();
		writer.println();
	}

	private static void testCombos(final ShinyWriter writer) {
		writer.println("combos    >");

		// Color + effect
		writer.println(ShinyEffects.BOLD.apply(ShinyColors.RED.fg(" bold red text")) + " and no effect");
		writer.println(ShinyEffects.UNDERLINE.apply(ShinyColors.BLUE.fg(" underlined blue text")) + " and no effect");
		writer.println(ShinyEffects.ITALIC.apply(ShinyColors.GREEN.fg(" italic green text")) + " and no effect");

		// Foreground 
		writer.println("this is a " + ShinyColors.YELLOW.fg("yellow") + " text with " + ShinyColors.RED.fg("red") + " and no effect");

		// Foreground + background
		writer.println(ShinyColors.YELLOW.fg(ShinyColors.BLUE.bg(" yellow on blue")) + " and no effect");
		writer.println(ShinyColors.CYAN_BRIGHT.fg(ShinyColors.RED.bg(" bright cyan on red")) + " and no effect");

		writer.println(ShinyColors.GREEN.fg(ShinyColors.WHITE.bg(" green on white") + " and green without bg") + " and no effect");
		writer.println(ShinyColors.CYAN_BRIGHT.fg(ShinyColors.RED.bg(" bright cyan on red")) + " and no effect");

		// Multiple effects
		String combo = ShinyEffects.BOLD.apply(
				ShinyEffects.UNDERLINE.apply(
						ShinyColors.MAGENTA_BRIGHT.fg(" bold underlined bright magenta")) + " and no effect");
		writer.println(combo);

		writer.println();
	}

	private static void testShake(final ShinyWriter writer) {
		String text;
		// --- Standard foreground + background
		writer.print("[fg= yellow, bg=red] ");
		text = ShinyColors.RED.bg(ShinyColors.YELLOW.fg("this is a beautiful text")) + " and no effect";
		writer.println(text);

		// --- Foreground + effect
		writer.print("[fg= blue, effect=inverse] ");
		text = ShinyEffects.INVERSE.apply(ShinyColors.BLUE.fg("this is a beautiful text")) + " and no effect";
		writer.println(text);

		// --- Foreground + background + effect
		writer.print("[fg= white bright, bg=green, effect=bold] ");
		text = ShinyEffects.BOLD.apply(ShinyColors.GREEN.bg(ShinyColors.WHITE_BRIGHT.fg("shiny combo")) + " and no effect");
		writer.println(text);

		writer.println();
		writer.println();
	}
}

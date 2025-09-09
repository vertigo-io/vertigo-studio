package io.vertigo.shiny.components.dataviz;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingScale;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingStyle;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingType;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyRatingTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicRatings(writer);
		testDifferentStyles(writer);
		testDifferentScales(writer);
		testCustomization(writer);
		testEdgeCases(writer);
	}

	private static void testBasicRatings(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Ratings ---"));

		Shiny.rating()
				.label("Movie Rating")
				.value(4.5)
				.render(writer);

		Shiny.rating()
				.label("User Experience")
				.value(3)
				.render(writer);

		Shiny.rating()
				.label("Service Quality")
				.value(5)
				.render(writer);

		writer.println();
	}

	private static void testDifferentStyles(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Different Rating Styles ---"));

		Shiny.rating()
				.label("Stars")
				.value(4)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.STAR))
				.render(writer);

		Shiny.rating()
				.label("Red Hearts")
				.value(4)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.HEART)
						.filledColor(ShinyColors.RED))
				.render(writer);

		Shiny.rating()
				.label("Blue Circles")
				.value(3)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.CIRCLE)
						.filledColor(ShinyColors.BLUE))
				.render(writer);

		Shiny.rating()
				.label("Green Squares")
				.value(5)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.SQUARE)
						.filledColor(ShinyColors.GREEN))
				.render(writer);

		Shiny.rating()
				.label("Magenta Diamonds")
				.value(2)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.DIAMOND)
						.filledColor(ShinyColors.MAGENTA))
				.render(writer);

		Shiny.rating()
				.label("Thumbs")
				.value(3)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.THUMB))
				.render(writer);

		Shiny.rating()
				.label("Fire")
				.value(4)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.FIRE))
				.render(writer);

		Shiny.rating()
				.label("Smiles")
				.value(5)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.SMILE))
				.render(writer);

		writer.println();
	}

	private static void testDifferentScales(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Different Rating Scales ---"));

		Shiny.rating()
				.label("5-Star Scale")
				.value(3.5)
				.scale(ShinyRatingScale.SCALE_5)
				.render(writer);

		Shiny.rating()
				.label("10-Point Scale")
				.value(8.5)
				.scale(ShinyRatingScale.SCALE_10)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.CIRCLE))
				.render(writer);

		Shiny.rating()
				.label("100-Point Scale")
				.value(85)
				.scale(ShinyRatingScale.SCALE_100)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.SQUARE)
						.filledColor(ShinyColors.CYAN))
				.render(writer);

		Shiny.rating()
				.label("Custom Scale (7)")
				.value(5)
				.maxValue(7)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.DIAMOND))
				.render(writer);

		Shiny.rating()
				.label("Custom Scale (3)")
				.value(2)
				.maxValue(3)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.HEART)
						.filledColor(ShinyColors.RED))
				.render(writer);

		writer.println();
	}

	private static void testCustomization(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Customized Ratings ---"));

		Shiny.rating()
				.label("Custom Colors")
				.value(4)
				.style(new ShinyRatingStyle()
						.filledColor(ShinyColors.MAGENTA)
						.emptyColor(ShinyColors.WHITE))
				.render(writer);

		Shiny.rating()
				.label("With Separators")
				.value(3)
				.separator(" | ")
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.ARROW))
				.render(writer);

		Shiny.rating()
				.label("No Value Display")
				.value(4)
				.showValue(false)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.STAR))
				.render(writer);

		Shiny.rating()
				.label("With Percentage")
				.value(3.5)
				.showPercentage(true)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.CIRCLE))
				.render(writer);

		Shiny.rating()
				.label("Value + Percentage")
				.value(4.2)
				.showValue(true)
				.showPercentage(true)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.HEART)
						.filledColor(ShinyColors.RED))
				.render(writer);

		Shiny.rating()
				.label("Half Ratings")
				.value(3.5)
				.allowHalfRating(true)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.STAR))
				.render(writer);

		Shiny.rating()
				.label("Boxed Rating")
				.value(5)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.DIAMOND)
						.filledColor(ShinyColors.YELLOW))
				.showBox(true)
				.render(writer);

		writer.println();
	}

	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Rating Edge Cases ---"));

		// Rating without label
		Shiny.rating()
				.value(3)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.STAR))
				.render(writer);

		// Zero rating
		Shiny.rating()
				.label("Zero Rating")
				.value(0)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.STAR))
				.render(writer);

		// Maximum rating
		Shiny.rating()
				.label("Perfect Score")
				.value(5)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.STAR))
				.render(writer);

		// Rating exceeding maximum (should be clamped)
		Shiny.rating()
				.label("Over Maximum")
				.value(7)
				.maxValue(5)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.STAR))
				.render(writer);

		// Very small custom scale
		Shiny.rating()
				.label("Binary Rating")
				.value(1)
				.maxValue(2)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.THUMB))
				.render(writer);

		// Very large custom scale
		Shiny.rating()
				.label("Large Scale")
				.value(15)
				.maxValue(20)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.DOT))
				.separator(" ")
				.showValue(false)
				.render(writer);

		// All styles showcase
		writer.println("\nAll styles showcase (3/5 rating):");
		for (final ShinyRatingType style : ShinyRatingType.values()) {
			Shiny.rating()
					.label(style.name())
					.value(3)
					.style(new ShinyRatingStyle()
							.type(style))
					.render(writer);
		}

		// All scales showcase
		writer.println("\nAll scales showcase:");
		Shiny.rating()
				.label("SCALE_5")
				.value(3.5)
				.scale(ShinyRatingScale.SCALE_5)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.STAR))
				.render(writer);

		Shiny.rating()
				.label("SCALE_10")
				.value(7.2)
				.scale(ShinyRatingScale.SCALE_10)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.CIRCLE))
				.render(writer);

		Shiny.rating()
				.label("SCALE_100")
				.value(72.5)
				.scale(ShinyRatingScale.SCALE_100)
				.style(new ShinyRatingStyle()
						.type(ShinyRatingType.SQUARE))
				.render(writer);

		writer.println();
	}
}

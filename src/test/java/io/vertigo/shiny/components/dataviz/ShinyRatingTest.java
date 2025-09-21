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

		Shiny.render(
				Shiny.rating()
						.withLabel("Movie Rating")
						.withValue(4.5)
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("User Experience")
						.withValue(3)
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Service Quality")
						.withValue(5)
						.build());

		writer.println();
	}

	private static void testDifferentStyles(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Different Rating Styles ---"));

		Shiny.render(
				Shiny.rating()
						.withLabel("Stars")
						.withValue(4)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.STAR))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Red Hearts")
						.withValue(4)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.HEART)
								.withFilledColor(ShinyColors.RED))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Blue Circles")
						.withValue(3)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.CIRCLE)
								.withFilledColor(ShinyColors.BLUE))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Green Squares")
						.withValue(5)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.SQUARE)
								.withFilledColor(ShinyColors.GREEN))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Magenta Diamonds")
						.withValue(2)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.DIAMOND)
								.withFilledColor(ShinyColors.MAGENTA))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Thumbs")
						.withValue(3)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.THUMB))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Fire")
						.withValue(4)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.FIRE))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Smiles")
						.withValue(5)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.SMILE))
						.build());

		writer.println();
	}

	private static void testDifferentScales(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Different Rating Scales ---"));

		Shiny.render(
				Shiny.rating()
						.withLabel("5-Star Scale")
						.withValue(3.5)
						.withScale(ShinyRatingScale.SCALE_5)
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("10-Point Scale")
						.withValue(8.5)
						.withScale(ShinyRatingScale.SCALE_10)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.CIRCLE))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("100-Point Scale")
						.withValue(85)
						.withScale(ShinyRatingScale.SCALE_100)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.SQUARE)
								.withFilledColor(ShinyColors.CYAN))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Custom Scale (7)")
						.withValue(5)
						.withMaxValue(7)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.DIAMOND))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Custom Scale (3)")
						.withValue(2)
						.withMaxValue(3)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.HEART)
								.withFilledColor(ShinyColors.RED))
						.build());

		writer.println();
	}

	private static void testCustomization(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Customized Ratings ---"));

		Shiny.render(
				Shiny.rating()
						.withLabel("Custom Colors")
						.withValue(4)
						.withStyle(new ShinyRatingStyle()
								.withFilledColor(ShinyColors.MAGENTA)
								.withEmptyColor(ShinyColors.WHITE))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("With Separators")
						.withValue(3)
						.withSeparator(" | ")
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.ARROW))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("No Value Display")
						.withValue(4)
						.withShowValue(false)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.STAR))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("With Percentage")
						.withValue(3.5)
						.withShowPercentage(true)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.CIRCLE))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Value + Percentage")
						.withValue(4.2)
						.withShowValue(true)
						.withShowPercentage(true)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.HEART)
								.withFilledColor(ShinyColors.RED))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Half Ratings")
						.withValue(3.5)
						.withAllowHalfRating(true)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.STAR))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("Boxed Rating")
						.withValue(5)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.DIAMOND)
								.withFilledColor(ShinyColors.YELLOW))
						.withShowBox(true)
						.build());

		writer.println();
	}

	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Rating Edge Cases ---"));

		// Rating without label
		Shiny.render(
				Shiny.rating()
						.withValue(3)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.STAR))
						.build());

		// Zero rating
		Shiny.render(
				Shiny.rating()
						.withLabel("Zero Rating")
						.withValue(0)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.STAR))
						.build());

		// Maximum rating
		Shiny.render(
				Shiny.rating()
						.withLabel("Perfect Score")
						.withValue(5)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.STAR))
						.build());

		// Rating exceeding maximum (should be clamped)
		Shiny.render(
				Shiny.rating()
						.withLabel("Over Maximum")
						.withValue(7)
						.withMaxValue(5)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.STAR))
						.build());

		// Very small custom scale
		Shiny.render(
				Shiny.rating()
						.withLabel("Binary Rating")
						.withValue(1)
						.withMaxValue(2)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.THUMB))
						.build());

		// Very large custom scale
		Shiny.render(
				Shiny.rating()
						.withLabel("Large Scale")
						.withValue(15)
						.withMaxValue(20)

						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.DOT))
						.withSeparator(" ")
						.withShowValue(false)
						.build());

		// All styles showcase
		writer.println("\nAll styles showcase (3/5 rating):");
		for (final ShinyRatingType style : ShinyRatingType.values()) {
			Shiny.render(
					Shiny.rating()
							.withLabel(style.name())
							.withValue(3)
							.withStyle(new ShinyRatingStyle()
									.withType(ShinyRatingType.STAR))
							.build());
		}

		// All scales showcase
		writer.println("\nAll scales showcase:");
		Shiny.render(
				Shiny.rating()
						.withLabel("SCALE_5")
						.withValue(3.5)
						.withScale(ShinyRatingScale.SCALE_5)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.STAR))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("SCALE_10")
						.withValue(7.2)
						.withScale(ShinyRatingScale.SCALE_10)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.CIRCLE))
						.build());

		Shiny.render(
				Shiny.rating()
						.withLabel("SCALE_100")
						.withValue(72.5)
						.withScale(ShinyRatingScale.SCALE_100)
						.withStyle(new ShinyRatingStyle()
								.withType(ShinyRatingType.SQUARE))
						.build());

		writer.println();
	}
}

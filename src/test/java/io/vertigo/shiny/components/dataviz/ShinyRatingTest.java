package io.vertigo.shiny.components.dataviz;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingScale;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingStyle;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyRatingTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicRatings(writer);
		testDifferentStyles(writer);
		testDifferentScales(writer);
		testCustomization(writer);
		//		testMultipleRatings();
		//		testDashboards();
		//		testSummaries();
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
				.style(ShinyRatingStyle.STAR)
				.render(writer);

		Shiny.rating()
				.label("Hearts")
				.value(4)
				.style(ShinyRatingStyle.HEART)
				.filledColor(ShinyColors.RED)
				.render(writer);

		Shiny.rating()
				.label("Circles")
				.value(3)
				.style(ShinyRatingStyle.CIRCLE)
				.filledColor(ShinyColors.BLUE)
				.render(writer);

		Shiny.rating()
				.label("Squares")
				.value(5)
				.style(ShinyRatingStyle.SQUARE)
				.filledColor(ShinyColors.GREEN)
				.render(writer);

		Shiny.rating()
				.label("Diamonds")
				.value(2)
				.style(ShinyRatingStyle.DIAMOND)
				.filledColor(ShinyColors.MAGENTA)
				.render(writer);

		Shiny.rating()
				.label("Thumbs")
				.value(3)
				.style(ShinyRatingStyle.THUMB)
				.render(writer);

		Shiny.rating()
				.label("Fire")
				.value(4)
				.style(ShinyRatingStyle.FIRE)
				.render(writer);

		Shiny.rating()
				.label("Smiles")
				.value(5)
				.style(ShinyRatingStyle.SMILE)
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
				.style(ShinyRatingStyle.CIRCLE)
				.render(writer);

		Shiny.rating()
				.label("100-Point Scale")
				.value(85)
				.scale(ShinyRatingScale.SCALE_100)
				.style(ShinyRatingStyle.SQUARE)
				.filledColor(ShinyColors.CYAN)
				.render(writer);

		Shiny.rating()
				.label("Custom Scale (7)")
				.value(5)
				.maxValue(7)
				.style(ShinyRatingStyle.DIAMOND)
				.render(writer);

		Shiny.rating()
				.label("Custom Scale (3)")
				.value(2)
				.maxValue(3)
				.style(ShinyRatingStyle.HEART)
				.filledColor(ShinyColors.RED)
				.render(writer);

		writer.println();
	}

	private static void testCustomization(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Customized Ratings ---"));

		Shiny.rating()
				.label("Custom Colors")
				.value(4)
				.filledColor(ShinyColors.MAGENTA)
				.emptyColor(ShinyColors.WHITE)
				.render(writer);

		Shiny.rating()
				.label("With Separators")
				.value(3)
				.separator(" | ")
				.style(ShinyRatingStyle.ARROW)
				.render(writer);

		Shiny.rating()
				.label("No Value Display")
				.value(4)
				.showValue(false)
				.style(ShinyRatingStyle.STAR)
				.render(writer);

		Shiny.rating()
				.label("With Percentage")
				.value(3.5)
				.showPercentage(true)
				.style(ShinyRatingStyle.CIRCLE)
				.render(writer);

		Shiny.rating()
				.label("Value + Percentage")
				.value(4.2)
				.showValue(true)
				.showPercentage(true)
				.style(ShinyRatingStyle.HEART)
				.filledColor(ShinyColors.RED)
				.render(writer);

		Shiny.rating()
				.label("Half Ratings")
				.value(3.5)
				.allowHalfRating(true)
				.style(ShinyRatingStyle.STAR)
				.render(writer);

		Shiny.rating()
				.label("Boxed Rating")
				.value(5)
				.style(ShinyRatingStyle.DIAMOND)
				.filledColor(ShinyColors.YELLOW)
				.showBox(true)
				.render(writer);

		writer.println();
	}

	//	private static void testMultipleRatings() {
	//		writer.println(ShinyColors.BLUE_BRIGHT + "--- Multiple Ratings (Aligned) ---" + ShinyColors.RESET);
	//
	//		final Map<String, Double> movieRatings = new LinkedHashMap<>();
	//		movieRatings.put("Action", 4.5);
	//		movieRatings.put("Comedy", 3.8);
	//		movieRatings.put("Drama", 4.9);
	//		movieRatings.put("Horror", 2.1);
	//		movieRatings.put("Romance", 3.5);
	//		movieRatings.put("Sci-Fi", 4.7);
	//
	//		ShinyRating.printMultiple(Shiny.getInstance(), movieRatings,
	//				ShinyRatingStyle.STAR,
	//				ShinyRating.RatingScale.SCALE_5);
	//		writer.println();
	//	}

	//	private static void testDashboards() {
	//		writer.println(ShinyColors.BLUE_BRIGHT + "--- Rating Dashboards ---" + ShinyColors.RESET);
	//
	//		// Restaurant Reviews Dashboard
	//		final Map<String, Double> restaurantRatings = new LinkedHashMap<>();
	//		restaurantRatings.put("Food Quality", 4.8);
	//		restaurantRatings.put("Service", 4.2);
	//		restaurantRatings.put("Atmosphere", 4.5);
	//		restaurantRatings.put("Value for Money", 3.9);
	//		restaurantRatings.put("Cleanliness", 4.7);
	//
	//		ShinyRating.printDashboard(Shiny.getInstance(), "Restaurant Reviews",
	//				restaurantRatings,
	//				ShinyRatingStyle.STAR,
	//				ShinyRating.RatingScale.SCALE_5);
	//
	//		// Product Features Dashboard (10-point scale)
	//		final Map<String, Double> productRatings = new LinkedHashMap<>();
	//		productRatings.put("Design", 8.5);
	//		productRatings.put("Performance", 9.2);
	//		productRatings.put("Battery Life", 7.8);
	//		productRatings.put("Camera", 8.9);
	//		productRatings.put("Price", 6.5);
	//
	//		ShinyRating.printDashboard(Shiny.getInstance(), "Product Features (10-point)",
	//				productRatings,
	//				ShinyRatingStyle.CIRCLE,
	//				ShinyRating.RatingScale.SCALE_10);
	//
	//		// Skill Assessment Dashboard (hearts)
	//		final Map<String, Double> skillRatings = new LinkedHashMap<>();
	//		skillRatings.put("Java", 4.0);
	//		skillRatings.put("Python", 3.5);
	//		skillRatings.put("JavaScript", 4.5);
	//		skillRatings.put("SQL", 3.8);
	//		skillRatings.put("Docker", 2.9);
	//
	//		ShinyRating.printDashboard(Shiny.getInstance(), "Programming Skills",
	//				skillRatings,
	//				ShinyRatingStyle.HEART,
	//				ShinyRating.RatingScale.SCALE_5);
	//	}

	//	private static void testSummaries() {
	//		writer.println(ShinyColors.BLUE_BRIGHT + "--- Rating Summaries ---" + ShinyColors.RESET);
	//
	//		// Customer Satisfaction Summary
	//		final Map<String, Double> satisfactionRatings = new LinkedHashMap<>();
	//		satisfactionRatings.put("Product Quality", 4.2);
	//		satisfactionRatings.put("Customer Service", 3.8);
	//		satisfactionRatings.put("Delivery Speed", 4.5);
	//		satisfactionRatings.put("Website UX", 3.9);
	//		satisfactionRatings.put("Value", 4.1);
	//
	//		ShinyRating.printSummary(Shiny.getInstance(), "Customer Satisfaction Survey",
	//				satisfactionRatings,
	//				ShinyRatingStyle.THUMB,
	//				ShinyRating.RatingScale.SCALE_5);
	//
	//		// Performance Metrics Summary (100-point scale)
	//		final Map<String, Double> performanceMetrics = new LinkedHashMap<>();
	//		performanceMetrics.put("CPU Usage", 75.5);
	//		performanceMetrics.put("Memory Usage", 68.2);
	//		performanceMetrics.put("Disk I/O", 82.1);
	//		performanceMetrics.put("Network", 91.8);
	//		performanceMetrics.put("Response Time", 85.6);
	//
	//		ShinyRating.printSummary(Shiny.getInstance(), "System Performance",
	//				performanceMetrics,
	//				ShinyRatingStyle.SQUARE,
	//				ShinyRating.RatingScale.SCALE_100);
	//	}
	//
	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Rating Edge Cases ---"));

		// Rating without label
		Shiny.rating()
				.value(3)
				.style(ShinyRatingStyle.STAR)
				.render(writer);

		// Zero rating
		Shiny.rating()
				.label("Zero Rating")
				.value(0)
				.style(ShinyRatingStyle.STAR)
				.render(writer);

		// Maximum rating
		Shiny.rating()
				.label("Perfect Score")
				.value(5)
				.style(ShinyRatingStyle.STAR)
				.render(writer);

		// Rating exceeding maximum (should be clamped)
		Shiny.rating()
				.label("Over Maximum")
				.value(7)
				.maxValue(5)
				.style(ShinyRatingStyle.STAR)
				.render(writer);

		// Very small custom scale
		Shiny.rating()
				.label("Binary Rating")
				.value(1)
				.maxValue(2)
				.style(ShinyRatingStyle.THUMB)
				.render(writer);

		// Very large custom scale
		Shiny.rating()
				.label("Large Scale")
				.value(15)
				.maxValue(20)
				.style(ShinyRatingStyle.DOT)
				.separator(" ")
				.showValue(false)
				.render(writer);

		// All styles showcase
		writer.println("\nAll styles showcase (3/5 rating):");
		for (final ShinyRatingStyle style : ShinyRatingStyle.values()) {
			Shiny.rating()
					.label(style.name())
					.value(3)
					.style(style)
					.render(writer);
		}

		// All scales showcase
		writer.println("\nAll scales showcase:");
		Shiny.rating()
				.label("SCALE_5")
				.value(3.5)
				.scale(ShinyRatingScale.SCALE_5)
				.style(ShinyRatingStyle.STAR)
				.render(writer);

		Shiny.rating()
				.label("SCALE_10")
				.value(7.2)
				.scale(ShinyRatingScale.SCALE_10)
				.style(ShinyRatingStyle.CIRCLE)
				.render(writer);

		Shiny.rating()
				.label("SCALE_100")
				.value(72.5)
				.scale(ShinyRatingScale.SCALE_100)
				.style(ShinyRatingStyle.SQUARE)
				.render(writer);

		writer.println();
	}
}

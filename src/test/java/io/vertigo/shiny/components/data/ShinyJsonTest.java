package io.vertigo.shiny.components.data;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.data.json.ShinyJsonStyle;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyJsonTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();

		testBasicJson(writer);
		testComplexJson(writer);
		testCustomColorsJson(writer);
		testGranularColorsJson(writer); // New test case
		testErrorJson(writer);
	}

	private static void testBasicJson(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic JSON ---"));
		final String json = """
				{
				"name": "John Doe",
				"age": 30,
				"isStudent": false,
				"address": null
				}
				""";
		Shiny.render(
				Shiny.json()
						.withJson(json)
						.build());
		writer.println();
	}

	private static void testComplexJson(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Complex JSON ---"));
		final String json = """
							{
							  "product": {
							    "id": 123,
							    "name": "Laptop",
							    "price": 1200.50,
							    "features": ["lightweight", "fast", "16GB RAM"],
							    "specs": {
							      "cpu": "Intel i7",
							      "storage": "512GB SSD"
							    },
							    "available": true
							  },"customer": null

				}""";
		Shiny.render(
				Shiny.json()
						.withJson(json)
						.build());
		writer.println();
	}

	private static void testCustomColorsJson(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Custom Colors JSON ---"));
		final String json = """
				{
				"city": "New York", "population": 8400000, "landmarks": ["Statue of Liberty", "Empire State Building"]
				}
				""";
		Shiny.render(
				Shiny.json()
						.withJson(json)
						.withStyle(new ShinyJsonStyle()
								.withLabelColor(ShinyColors.CYAN)
								.withNumberColor(ShinyColors.MAGENTA)
								.withStringColor(ShinyColors.YELLOW)
								.withColonColor(ShinyColors.GREEN)
								.withBracketColor(ShinyColors.RED)
								.withBracesColor(ShinyColors.RED))
						.build());
		writer.println();
	}

	private static void testGranularColorsJson(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Granular Colors JSON ---"));
		final String json = """
					{
							"id": 1,
							"name": "Test Item",
							"value": 123.45,
							"active": true,
							"tags": ["alpha", "beta"],
							"details": null
					}
				""";
		Shiny.render(
				Shiny.json()
						.withJson(json)
						.withStyle(new ShinyJsonStyle()
								.withLabelColor(ShinyColors.BLUE_BRIGHT)
								.withStringColor(ShinyColors.GREEN_BRIGHT)
								.withNumberColor(ShinyColors.CYAN_BRIGHT)
								.withBracesColor(ShinyColors.RED)
								.withBracketColor(ShinyColors.MAGENTA)
								.withColonColor(ShinyColors.YELLOW)
								.withCommaColor(ShinyColors.WHITE)
								.withBooleanColor(ShinyColors.GREEN)
								.withNullColor(ShinyColors.RED))
						.build());
		writer.println();
	}

	private static void testErrorJson(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Error JSON ---"));
		final String json = """
					{
				"name": "John Doe",
				"age": 30,
				"isStudent": false,
				"address": null
				"""; // Missing closing brace
		Shiny.render(
				Shiny.json()
						.withJson(json)
						.build());
		writer.println();
	}
}

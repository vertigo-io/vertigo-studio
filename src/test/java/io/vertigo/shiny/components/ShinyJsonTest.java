package io.vertigo.shiny.components;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyJsonTest {

	public static void main(final String[] args) {
		testBasicJson();
		testComplexJson();
		testCustomColorsJson();
		testGranularColorsJson(); // New test case
		testErrorJson();
	}

	private static void testBasicJson() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic JSON ---"));
		final String json = "{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": false, \"address\": null}";
		Shiny.json()
				.json(json)
				.print();
		System.out.println();
	}

	private static void testComplexJson() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Complex JSON ---"));
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
		Shiny.json().json(json).print();
		System.out.println();
	}

	private static void testCustomColorsJson() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Custom Colors JSON ---"));
		final String json = """
				{
				"city": "New York", "population": 8400000, "landmarks": ["Statue of Liberty", "Empire State Building"]
				}
				""";
		Shiny.json()
				.json(json)
				.labelColor(ShinyColors.CYAN)
				.numberColor(ShinyColors.MAGENTA)
				.stringColor(ShinyColors.YELLOW)
				.colonColor(ShinyColors.GREEN)
				.bracketColor(ShinyColors.RED)
				.bracesColor(ShinyColors.RED)
				.print();
		System.out.println();
	}

	private static void testGranularColorsJson() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Granular Colors JSON ---"));
		final String json = "{\"id\": 1, \"name\": \"Test Item\", \"value\": 123.45, \"active\": true, \"tags\": [\"alpha\", \"beta\"], \"details\": null}";
		Shiny.json()
				.json(json)
				.labelColor(ShinyColors.BLUE_BRIGHT)
				.stringColor(ShinyColors.GREEN_BRIGHT)
				.numberColor(ShinyColors.CYAN_BRIGHT)
				.bracesColor(ShinyColors.RED)
				.bracketColor(ShinyColors.MAGENTA)
				.colonColor(ShinyColors.YELLOW)
				.commaColor(ShinyColors.WHITE)
				.booleanColor(ShinyColors.GREEN)
				.nullColor(ShinyColors.RED)
				.print();
		System.out.println();
	}

	private static void testErrorJson() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Error JSON ---"));
		final String json = "{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": false, \"address\": null"; // Missing closing brace
		Shiny.json().json(json).print();
		System.out.println();
	}
}

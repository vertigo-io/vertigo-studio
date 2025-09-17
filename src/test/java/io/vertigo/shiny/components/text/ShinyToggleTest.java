package io.vertigo.shiny.components.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.text.toggle.ShinyToggleStyle;
import io.vertigo.shiny.components.text.toggle.ShinyToggleType;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyToggleTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicToggles(writer);
		testDifferentStyles(writer);
		testCustomization(writer);
		//testMultipleToggles();
		//testDashboards();
		//testEdgeCases();
	}

	private static void testBasicToggles(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Toggles ---"));

		Shiny.toggle()
				.withLabel("WiFi Connection")
				.withValue(true)
				.render(writer);

		Shiny.toggle()
				.withLabel("Bluetooth")
				.withValue(false)
				.render(writer);

		Shiny.toggle()
				.withLabel("Auto-save")
				.withValue(true)
				.render(writer);

		writer.println();
	}

	private static void testDifferentStyles(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Different Toggle Styles ---"));

		Shiny.toggle()
				.withLabel("Classic Style")
				.withValue(true)
				.withType(ShinyToggleType.CLASSIC)
				.render(writer);

		Shiny.toggle()
				.withLabel("Switch Style")
				.withValue(false)
				.withType(ShinyToggleType.SWITCH)
				.render(writer);

		Shiny.toggle()
				.withLabel("Light Bulb")
				.withValue(true)
				.withType(ShinyToggleType.LIGHT)
				.render(writer);

		Shiny.toggle()
				.withLabel("Battery Level")
				.withValue(false)
				.withType(ShinyToggleType.BATTERY)
				.render(writer);

		Shiny.toggle()
				.withLabel("Server Status")
				.withValue(true)
				.withType(ShinyToggleType.STATUS)
				.render(writer);

		Shiny.toggle()
				.withLabel("User Rating")
				.withValue(false)
				.withType(ShinyToggleType.THUMBS)
				.render(writer);

		writer.println();
	}

	private static void testCustomization(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Customized Toggles ---"));

		Shiny.toggle()
				.withLabel("Custom Colors")
				.withValue(true)
				.withStyle(new ShinyToggleStyle()
						.withOnColor(ShinyColors.CYAN)
						.withOffColor(ShinyColors.MAGENTA))
				.render(writer);

		Shiny.toggle()
				.withLabel("Custom Text")
				.withValue(false)
				.withOnText("ENABLED")
				.withOffText("DISABLED")
				.withType(ShinyToggleType.ARROW)
				.render(writer);

		Shiny.toggle()
				.withLabel("No Text")
				.withValue(true)
				.withShowText(false)
				.withType(ShinyToggleType.CHECK)
				.render(writer);

		Shiny.toggle()
				.withLabel("With star-on")
				.withValue(true)
				.withType(ShinyToggleType.STAR)
				.render(writer);
		Shiny.toggle()
				.withLabel("With star-off")
				.withValue(false)
				.withType(ShinyToggleType.STAR)
				.render(writer);

		writer.println();
	}

}
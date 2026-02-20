package io.vertigo.shiny.models.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.input.toggle.ShinyToggleType;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyToggleTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = ShinyRenderer.writer();
		testBasicToggles(writer);
		testDifferentStyles(writer);
		testCustomization(writer);
		//testMultipleToggles();
		//testDashboards();
		//testEdgeCases();
	}

	private static void testBasicToggles(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Toggles ---"));

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("WiFi Connection")
						.withValue(true)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Bluetooth")
						.withValue(false)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Auto-save")
						.withValue(true)
						.build());

		writer.println();
	}

	private static void testDifferentStyles(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Different Toggle Styles ---"));

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Classic Style")
						.withValue(true)
						.withType(ShinyToggleType.CLASSIC)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Switch Style")
						.withValue(false)
						.withType(ShinyToggleType.SWITCH)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Light Bulb")
						.withValue(true)
						.withType(ShinyToggleType.LIGHT)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Battery Level")
						.withValue(false)
						.withType(ShinyToggleType.BATTERY)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Server Status")
						.withValue(true)
						.withType(ShinyToggleType.STATUS)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("User Rating")
						.withValue(false)
						.withType(ShinyToggleType.THUMBS)
						.build());

		writer.println();
	}

	private static void testCustomization(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Customized Toggles ---"));

		ShinyRenderer.theme().toggleStyle()
				.withOnColor(ShinyColors.CYAN)
				.withOffColor(ShinyColors.MAGENTA);

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Custom Colors")
						.withValue(true)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("Custom Text")
						.withValue(false)
						.withOnText("ENABLED")
						.withOffText("DISABLED")
						.withType(ShinyToggleType.ARROW)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("No Text")
						.withValue(true)
						.withShowText(false)
						.withType(ShinyToggleType.CHECK)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("With star-on")
						.withValue(true)
						.withType(ShinyToggleType.STAR)
						.build());

		ShinyRenderer.render(
				Shiny.toggle()
						.withLabel("With star-off")
						.withValue(false)
						.withType(ShinyToggleType.STAR)
						.build());

		writer.println();
	}
}

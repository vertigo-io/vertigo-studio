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
				.label("WiFi Connection")
				.value(true)
				.render(writer);

		Shiny.toggle()
				.label("Bluetooth")
				.value(false)
				.render(writer);

		Shiny.toggle()
				.label("Auto-save")
				.value(true)
				.render(writer);

		writer.println();
	}

	private static void testDifferentStyles(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Different Toggle Styles ---"));

		Shiny.toggle()
				.label("Classic Style")
				.value(true)
				.type(ShinyToggleType.CLASSIC)
				.render(writer);

		Shiny.toggle()
				.label("Switch Style")
				.value(false)
				.type(ShinyToggleType.SWITCH)
				.render(writer);

		Shiny.toggle()
				.label("Light Bulb")
				.value(true)
				.type(ShinyToggleType.LIGHT)
				.render(writer);

		Shiny.toggle()
				.label("Battery Level")
				.value(false)
				.type(ShinyToggleType.BATTERY)
				.render(writer);

		Shiny.toggle()
				.label("Server Status")
				.value(true)
				.type(ShinyToggleType.STATUS)
				.render(writer);

		Shiny.toggle()
				.label("User Rating")
				.value(false)
				.type(ShinyToggleType.THUMBS)
				.render(writer);

		writer.println();
	}

	private static void testCustomization(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Customized Toggles ---"));

		Shiny.toggle()
				.label("Custom Colors")
				.value(true)
				.style(new ShinyToggleStyle()
						.onColor(ShinyColors.CYAN)
						.offColor(ShinyColors.MAGENTA))
				.render(writer);

		Shiny.toggle()
				.label("Custom Text")
				.value(false)
				.onText("ENABLED")
				.offText("DISABLED")
				.type(ShinyToggleType.ARROW)
				.render(writer);

		Shiny.toggle()
				.label("No Text")
				.value(true)
				.showText(false)
				.type(ShinyToggleType.CHECK)
				.render(writer);

		Shiny.toggle()
				.label("With star-on")
				.value(true)
				.type(ShinyToggleType.STAR)
				.render(writer);
		Shiny.toggle()
				.label("With star-off")
				.value(false)
				.type(ShinyToggleType.STAR)
				.render(writer);

		writer.println();
	}

}

package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.toggle.ShinyToggleStyle;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyToggleTest {

	public static void main(final String[] args) {
		testBasicToggles();
		testDifferentStyles();
		testCustomization();
		//testMultipleToggles();
		//testDashboards();
		//testEdgeCases();
	}

	private static void testBasicToggles() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Basic Toggles ---" + ShinyColors.RESET);

		Shiny.toggle()
				.label("WiFi Connection")
				.value(true)
				.print();

		Shiny.toggle()
				.label("Bluetooth")
				.value(false)
				.print();

		Shiny.toggle()
				.label("Auto-save")
				.value(true)
				.print();

		System.out.println();
	}

	private static void testDifferentStyles() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Different Toggle Styles ---" + ShinyColors.RESET);

		Shiny.toggle()
				.label("Classic Style")
				.value(true)
				.style(ShinyToggleStyle.CLASSIC)
				.print();

		Shiny.toggle()
				.label("Switch Style")
				.value(false)
				.style(ShinyToggleStyle.SWITCH)
				.print();

		Shiny.toggle()
				.label("Light Bulb")
				.value(true)
				.style(ShinyToggleStyle.LIGHT)
				.print();

		Shiny.toggle()
				.label("Battery Level")
				.value(false)
				.style(ShinyToggleStyle.BATTERY)
				.print();

		Shiny.toggle()
				.label("Server Status")
				.value(true)
				.style(ShinyToggleStyle.STATUS)
				.print();

		Shiny.toggle()
				.label("User Rating")
				.value(false)
				.style(ShinyToggleStyle.THUMBS)
				.print();

		System.out.println();
	}

	private static void testCustomization() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Customized Toggles ---" + ShinyColors.RESET);

		Shiny.toggle()
				.label("Custom Colors")
				.value(true)
				.onColor(ShinyColors.CYAN)
				.offColor(ShinyColors.MAGENTA)
				.print();

		Shiny.toggle()
				.label("Custom Text")
				.value(false)
				.onText("ENABLED")
				.offText("DISABLED")
				.style(ShinyToggleStyle.ARROW)
				.print();

		Shiny.toggle()
				.label("No Text")
				.value(true)
				.showText(false)
				.style(ShinyToggleStyle.CHECK)
				.print();

		Shiny.toggle()
				.label("With Box")
				.value(true)
				.style(ShinyToggleStyle.STAR)
				//			.showBox(true)
				.print();

		System.out.println();
	}

	/*	private static void testMultipleToggles() {
			System.out.println(ShinyColors.BLUE_BRIGHT + "--- Multiple Toggles (Aligned) ---" + ShinyColors.RESET);
	
			final Map<String, Boolean> settings = new LinkedHashMap<>();
			settings.put("Auto-save", true);
			settings.put("Dark Mode", false);
			settings.put("Notifications", true);
			settings.put("Sound Effects", false);
			settings.put("Auto-update", true);
			settings.put("Analytics", false);
	
			ShinyToggle.printMultiple(Shiny.getInstance(), settings, ShinyToggleStyle.TOGGLE);
			System.out.println();
		}
	
		private static void testDashboards() {
			System.out.println(ShinyColors.BLUE_BRIGHT + "--- Toggle Dashboards ---" + ShinyColors.RESET);
	
			// System Status Dashboard
			final Map<String, Boolean> systemStatus = new LinkedHashMap<>();
			systemStatus.put("CPU", true);
			systemStatus.put("Memory", true);
			systemStatus.put("Disk Space", false);
			systemStatus.put("Network", true);
			systemStatus.put("Database", false);
			systemStatus.put("Cache", true);
	
			ShinyToggle.printDashboard(Shiny.getInstance(), "System Health Monitor", systemStatus, ShinyToggleStyle.STATUS);
	
			// Feature Flags Dashboard
			final Map<String, Boolean> featureFlags = new LinkedHashMap<>();
			featureFlags.put("New UI", true);
			featureFlags.put("Beta Features", false);
			featureFlags.put("A/B Testing", true);
			featureFlags.put("Debug Mode", false);
			featureFlags.put("Maintenance", false);
	
			ShinyToggle.printDashboard(Shiny.getInstance(), "Feature Flags", featureFlags, ShinyToggleStyle.SWITCH);
	
			// User Preferences Dashboard
			final Map<String, Boolean> userPrefs = new LinkedHashMap<>();
			userPrefs.put("Email Notifications", true);
			userPrefs.put("Push Notifications", false);
			userPrefs.put("Marketing Emails", false);
			userPrefs.put("Weekly Reports", true);
			userPrefs.put("Security Alerts", true);
	
			ShinyToggle.printDashboard(Shiny.getInstance(), "User Preferences", userPrefs, ShinyToggleStyle.CHECK);
		}
	
		private static void testEdgeCases() {
			System.out.println(ShinyColors.BLUE_BRIGHT + "--- Toggle Edge Cases ---" + ShinyColors.RESET);
	
			// Toggle without label
			Shiny.toggle()
					.value(true)
					.style(ShinyToggleStyle.CLASSIC)
					.print();
	
			// Toggle with empty label
			Shiny.toggle()
					.label("")
					.value(false)
					.style(ShinyToggleStyle.SWITCH)
					.print();
	
			// Toggle with very long label
			Shiny.toggle()
					.label("This is a very long label to test text wrapping and alignment")
					.value(true)
					.style(ShinyToggleStyle.LIGHT)
					.showBox(true)
					.print();
	
			// Multiple styles in sequence
			System.out.println("\nAll styles showcase:");
			for (ShinyToggleStyle style : ShinyToggleStyle.values()) {
				Shiny.toggle()
						.label(style.name())
						.value(true)
						.style(style)
						.print();
			}
	
			System.out.println("\nAll styles OFF:");
			for (ShinyToggleStyle style : ShinyToggleStyle.values()) {
				Shiny.toggle()
						.label(style.name())
						.value(false)
						.style(style)
						.print();
			}
	
			System.out.println();
		}
		*/
}

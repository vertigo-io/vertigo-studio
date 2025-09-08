package io.vertigo.shiny.components.input;

import java.util.List;
import java.util.Scanner;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.input.text.ShinyInputText;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyInputTextTest {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();

		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Starting ShinyInputText Component Test ---"));
		writer.println("Follow the prompts to test different input scenarios.");
		writer.println();

		testBasicInput(writer);
		testRequiredInput(writer);
		testPatternValidation(writer);
		testDefaultValue(writer);
		testSuggestions(writer);
		testSecretInput(writer);

		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- ShinyInputText Component Test Finished ---"));
		scanner.close();
	}

	private static void waitForEnter(final ShinyWriter writer) {
		writer.println(ShinyColors.YELLOW.fg("Press Enter to continue..."));
		scanner.nextLine();
		writer.println();
	}

	private static void testBasicInput(final ShinyWriter writer) {
		writer.println(ShinyColors.CYAN.fg("Scenario: Basic Input"));
		writer.println("Parameters: label='Enter your name'");
		final ShinyInputText inputText = Shiny.inputText().label("Enter your name");
		inputText.render(writer);
		writer.println("You entered: " + inputText.getValue());
		waitForEnter(writer);
	}

	private static void testRequiredInput(final ShinyWriter writer) {
		writer.println(ShinyColors.CYAN.fg("Scenario: Required Input"));
		writer.println("Parameters: label='Enter a value (required)'");
		writer.println("Try leaving it empty first, then enter a value.");
		final ShinyInputText inputText = Shiny.inputText().label("Enter a value").required(true);
		inputText.render(writer);
		writer.println("You entered: " + inputText.getValue());
		waitForEnter(writer);
	}

	private static void testPatternValidation(final ShinyWriter writer) {
		writer.println(ShinyColors.CYAN.fg("Scenario: Pattern Validation (5 lowercase letters)"));
		writer.println("Parameters: label='Enter 5 lowercase letters', pattern='[a-z]{5}'");
		writer.println("Try invalid input (e.g., '12345', 'abc'), then valid (e.g., 'hello').");
		final ShinyInputText inputText = Shiny.inputText().label("Enter 5 lowercase letters").pattern("[a-z]{5}");
		inputText.render(writer);
		writer.println("You entered: " + inputText.getValue());
		waitForEnter(writer);
	}

	private static void testDefaultValue(final ShinyWriter writer) {
		writer.println(ShinyColors.CYAN.fg("Scenario: Default Value"));
		writer.println("Parameters: label='Enter your age', defaultValue='30'");
		writer.println("Press Enter without typing to use the default value.");
		final ShinyInputText inputText = Shiny.inputText().label("Enter your age").defaultValue("30");
		inputText.render(writer);
		writer.println("You entered: " + inputText.getValue());
		waitForEnter(writer);
	}

	private static void testSuggestions(final ShinyWriter writer) {
		writer.println(ShinyColors.CYAN.fg("Scenario: Suggestions"));
		writer.println("Parameters: label='Choose your favorite color', suggestions=['red', 'green', 'blue']");
		writer.println("Type anything, the suggestions are just visual hints.");
		final ShinyInputText inputText = Shiny.inputText().label("Choose your favorite color").suggestions(List.of("red", "green", "blue"));
		inputText.render(writer);
		writer.println("You entered: " + inputText.getValue());
		waitForEnter(writer);
	}

	private static void testSecretInput(final ShinyWriter writer) {
		writer.println(ShinyColors.CYAN.fg("Scenario: Secret Input"));
		writer.println("Parameters: label='Enter your password', secret=true");
		writer.println("Note: Input will be echoed in this basic console, but conceptually it's masked.");
		final ShinyInputText inputText = Shiny.inputText().label("Enter your password").secret(true);
		inputText.render(writer);
		writer.println("You entered: " + inputText.getValue());
		waitForEnter(writer);
	}
}

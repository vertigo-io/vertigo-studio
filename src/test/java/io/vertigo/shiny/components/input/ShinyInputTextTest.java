package io.vertigo.shiny.components.input;

import java.util.List;
import java.util.Scanner;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.input.text.ShinyInputText;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyInputTextTest {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(final String[] args) {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Starting ShinyInputText Component Test ---" + ShinyColors.RESET);
		System.out.println("Follow the prompts to test different input scenarios.");
		System.out.println();

		testBasicInput();
		testRequiredInput();
		testPatternValidation();
		testDefaultValue();
		testSuggestions();
		testSecretInput();

		System.out.println(ShinyColors.BLUE_BRIGHT + "--- ShinyInputText Component Test Finished ---" + ShinyColors.RESET);
		scanner.close();
	}

	private static void waitForEnter() {
		System.out.println(ShinyColors.YELLOW + "Press Enter to continue..." + ShinyColors.RESET);
		scanner.nextLine();
		System.out.println();
	}

	private static void testBasicInput() {
		System.out.println(ShinyColors.CYAN + "Scenario: Basic Input" + ShinyColors.RESET);
		System.out.println("Parameters: label='Enter your name'");
		final ShinyInputText inputText = Shiny.inputText().label("Enter your name");
		inputText.print();
		System.out.println("You entered: " + inputText.getValue());
		waitForEnter();
	}

	private static void testRequiredInput() {
		System.out.println(ShinyColors.CYAN + "Scenario: Required Input" + ShinyColors.RESET);
		System.out.println("Parameters: label='Enter a value (required)'");
		System.out.println("Try leaving it empty first, then enter a value.");
		final ShinyInputText inputText = Shiny.inputText().label("Enter a value").required(true);
		inputText.print();
		System.out.println("You entered: " + inputText.getValue());
		waitForEnter();
	}

	private static void testPatternValidation() {
		System.out.println(ShinyColors.CYAN + "Scenario: Pattern Validation (5 lowercase letters)" + ShinyColors.RESET);
		System.out.println("Parameters: label='Enter 5 lowercase letters', pattern='[a-z]{5}'");
		System.out.println("Try invalid input (e.g., '12345', 'abc'), then valid (e.g., 'hello').");
		final ShinyInputText inputText = Shiny.inputText().label("Enter 5 lowercase letters").pattern("[a-z]{5}");
		inputText.print();
		System.out.println("You entered: " + inputText.getValue());
		waitForEnter();
	}

	private static void testDefaultValue() {
		System.out.println(ShinyColors.CYAN + "Scenario: Default Value" + ShinyColors.RESET);
		System.out.println("Parameters: label='Enter your age', defaultValue='30'");
		System.out.println("Press Enter without typing to use the default value.");
		final ShinyInputText inputText = Shiny.inputText().label("Enter your age").defaultValue("30");
		inputText.print();
		System.out.println("You entered: " + inputText.getValue());
		waitForEnter();
	}

	private static void testSuggestions() {
		System.out.println(ShinyColors.CYAN + "Scenario: Suggestions" + ShinyColors.RESET);
		System.out.println("Parameters: label='Choose your favorite color', suggestions=['red', 'green', 'blue']");
		System.out.println("Type anything, the suggestions are just visual hints.");
		final ShinyInputText inputText = Shiny.inputText().label("Choose your favorite color").suggestions(List.of("red", "green", "blue"));
		inputText.print();
		System.out.println("You entered: " + inputText.getValue());
		waitForEnter();
	}

	private static void testSecretInput() {
		System.out.println(ShinyColors.CYAN + "Scenario: Secret Input" + ShinyColors.RESET);
		System.out.println("Parameters: label='Enter your password', secret=true");
		System.out.println("Note: Input will be echoed in this basic console, but conceptually it's masked.");
		final ShinyInputText inputText = Shiny.inputText().label("Enter your password").secret(true);
		inputText.print();
		System.out.println("You entered: " + inputText.getValue());
		waitForEnter();
	}
}

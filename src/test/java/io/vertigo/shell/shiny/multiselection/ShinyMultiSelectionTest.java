package io.vertigo.shell.shiny.multiselection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.input.multiselection.ShinyMultiSelection;

public class ShinyMultiSelectionTest {
	public static void main(final String[] args) {
		testBasicMultiSelectionStrict();
		if (1 == 9) {
			testInitialSelectedMultiSelectionStrict();
			testInvalidInputStrict();
		}
	}

	private static void testBasicMultiSelectionStrict() {
		// Simulate user input: select options 1 and 3
		//		final String simulatedInput = "1,3\n";
		//		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		// Capture console output
		//		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		///		System.setOut(new PrintStream(baos));

		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Choose your favorite fruits:")
				.options("Apple", "Banana", "Cherry", "Date")
				.strict(); // Enable strict mode

		multiSelection.print();

		final List<String> selected = multiSelection.getSelectedOptions();
		System.out.println(selected);
		//		assertEquals(2, selected.size());
		//		assertTrue(selected.contains("Apple"));
		//		assertTrue(selected.contains("Cherry"));
		//
		//		// Restore original System.in and System.out
		//		System.setIn(System.in);
		//		System.setOut(System.out);
		//
		//		final String output = baos.toString();
		//		assertTrue(output.contains("Choose your favorite fruits:"));
		//		assertTrue(output.contains("[ ] 1. Apple"));
		//		assertTrue(output.contains("[ ] 2. Banana"));
		//		assertTrue(output.contains("[ ] 3. Cherry"));
		//		assertTrue(output.contains("[ ] 4. Date"));
		//		assertTrue(output.contains("Please enter the numbers of the options you want to select, separated by commas (e.g., 1,3,5):"));
	}

	private static void testInitialSelectedMultiSelectionStrict() {
		final String simulatedInput = "\n"; // No new selection, keep initial
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));

		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Choose your favorite colors:")
				.options("Red", "Green", "Blue", "Yellow")
				.selected(List.of("Green", "Yellow"))
				.strict();

		multiSelection.print();

		final List<String> selected = multiSelection.getSelectedOptions();
		assertEquals(2, selected.size());
		assertTrue(selected.contains("Green"));
		assertTrue(selected.contains("Yellow"));

		System.setIn(System.in);
		System.setOut(System.out);
	}

	private static void testInvalidInputStrict() {
		final String simulatedInput = "1,abc,3\n"; // Invalid input
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));

		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Test Invalid Input:")
				.options("One", "Two", "Three")
				.strict();

		multiSelection.print();

		final List<String> selected = multiSelection.getSelectedOptions();
		assertTrue(selected.isEmpty()); // Should be empty due to error

		System.setIn(System.in);
		System.setOut(System.out);

		final String output = baos.toString();
		assertTrue(output.contains("Invalid input. Please try again."));
	}
}

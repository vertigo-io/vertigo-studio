package io.vertigo.shiny.components.input;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.input.text.ShinyInputText;

public class ShinyInputTextTest {

	@Test
	public static void testSimpleInput() {
		String simulatedInput = "Hello World\n";
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		ShinyInputText input = Shiny.inputText()
				.withLabel("Enter your name")
				.build();

		Shiny.render(input);

		Assertions.assertEquals("Hello World", input.value().get());
	}

	@Test
	public void testRequiredInput_FailThenSuccess() {
		String simulatedInput = "\nHello World\n"; // First empty, then valid
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		ShinyInputText input = Shiny.inputText()
				.withLabel("Enter something")
				.withRequired(true)
				.build();

		Shiny.render(input);

		Assertions.assertEquals("Hello World", input.value().get());
	}

	@Test
	public void testPatternInput_FailThenSuccess() {
		String simulatedInput = "abc\n12345\n"; // First invalid, then valid
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		ShinyInputText input = Shiny.inputText()
				.withLabel("Enter a 5-digit number")
				.withPattern("\\d{5}")
				.build();

		Shiny.render(input);

		Assertions.assertEquals("12345", input.value().get());
	}

	@Test
	public void testSuggestions() {
		String simulatedInput = "red\n";
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		ShinyInputText input = Shiny.inputText()
				.withLabel("Choose a color")
				.addAllSuggestions(Arrays.asList("red", "green", "blue"))
				.build();

		Shiny.render(input);

		Assertions.assertEquals("red", input.value().get());
	}

	@Test
	public void testDefaultValue() {
		String simulatedInput = "\n"; // User just presses Enter
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		ShinyInputText input = Shiny.inputText()
				.withLabel("Enter your city")
				.withDefaultValue("Paris")
				.build();

		Shiny.render(input);

		Assertions.assertEquals("Paris", input.value().get());
	}
}

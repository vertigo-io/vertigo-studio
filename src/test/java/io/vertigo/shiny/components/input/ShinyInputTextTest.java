package io.vertigo.shiny.components.input;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.input.text.ShinyInputText;

public class ShinyInputTextTest {

    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() {
        // Redirect System.in to a mock input stream if needed
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    public void testSimpleInput() {
        String simulatedInput = "Hello World\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ShinyInputText input = Shiny.ui().newInputText()
                                  .withLabel("Enter your name");

        Shiny.render(input);

        Assertions.assertEquals("Hello World", input.getValue());
    }

    @Test
    public void testRequiredInput_FailThenSuccess() {
        String simulatedInput = "\nHello World\n"; // First empty, then valid
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ShinyInputText input = Shiny.ui().newInputText()
                                  .withLabel("Enter something")
                                  .withRequired(true);

        Shiny.render(input);

        Assertions.assertEquals("Hello World", input.getValue());
    }

    @Test
    public void testPatternInput_FailThenSuccess() {
        String simulatedInput = "abc\n12345\n"; // First invalid, then valid
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ShinyInputText input = Shiny.ui().newInputText()
                                  .withLabel("Enter a 5-digit number")
                                  .withPattern("\\d{5}");

        Shiny.render(input);

        Assertions.assertEquals("12345", input.getValue());
    }
    
    @Test
    public void testSuggestions() {
        String simulatedInput = "red\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ShinyInputText input = Shiny.ui().newInputText()
                                  .withLabel("Choose a color")
                                  .addAllSuggestions(Arrays.asList("red", "green", "blue"));

        Shiny.render(input);

        Assertions.assertEquals("red", input.getValue());
    }

    @Test
    public void testDefaultValue() {
        String simulatedInput = "\n"; // User just presses Enter
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ShinyInputText input = Shiny.ui().newInputText()
                                  .withLabel("Enter your city")
                                  .withDefaultValue("Paris");

        Shiny.render(input);

        Assertions.assertEquals("Paris", input.getValue());
    }
}
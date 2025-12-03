package io.vertigo.vortex.model.types.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXRequiredValidatorTest {

    private final VXRequiredValidator validator = new VXRequiredValidator();

    @Test
    void shouldValidateNonNullObject() {
        // Given
        final Object nonNullObject = new Object();
        // When
        Assertions.assertTrue(validator.isValid(nonNullObject));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateNullObject() {
        // Given
        final Object nullObject = null;
        // When
        Assertions.assertFalse(validator.isValid(nullObject));
    }

    @Test
    void shouldValidateNonBlankString() {
        // Given
        final String nonBlankString = "some text";
        // When
        Assertions.assertTrue(validator.isValid(nonBlankString));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateBlankString() {
        // Given
        final String blankString = "   ";
        // When
        Assertions.assertFalse(validator.isValid(blankString));
    }

    @Test
    void shouldNotValidateEmptyString() {
        // Given
        final String emptyString = "";
        // When
        Assertions.assertFalse(validator.isValid(emptyString));
    }

    @Test
    void shouldNotValidateNullString() {
        // Given
        final String nullString = null;
        // When
        Assertions.assertFalse(validator.isValid(nullString));
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("This field is required", errorMessage);
    }

    @Test
    void shouldReturnCorrectProperty() {
        // When
        final var property = validator.getProperty();
        // Then
        Assertions.assertTrue(property.isPresent());
        Assertions.assertEquals("required", property.get().name());
        Assertions.assertEquals(Boolean.class, property.get().type());
    }
}

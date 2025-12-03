package io.vertigo.vortex.model.types.validators.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.model.types.validators.VXMaxValidator;

public class VXMaxValidatorTest {

    @Test
    void shouldValidateIntegerLessThanMax() {
        // Given
        final VXMaxValidator validator = new VXMaxValidator(10);
        // When
        Assertions.assertTrue(validator.isValid(5));
        // Then - no exception is thrown
    }

    @Test
    void shouldValidateIntegerEqualToMax() {
        // Given
        final VXMaxValidator validator = new VXMaxValidator(10);
        // When
        Assertions.assertTrue(validator.isValid(10));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateIntegerGreaterThanMax() {
        // Given
        final VXMaxValidator validator = new VXMaxValidator(10);
        // When
        Assertions.assertFalse(validator.isValid(15));
    }

    @Test
    void shouldValidateDoubleLessThanMax() {
        // Given
        final VXMaxValidator validator = new VXMaxValidator(10.5);
        // When
        Assertions.assertTrue(validator.isValid(5.5));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateDoubleGreaterThanMax() {
        // Given
        final VXMaxValidator validator = new VXMaxValidator(10.5);
        // When
        Assertions.assertFalse(validator.isValid(15.5));
    }

    @Test
    void shouldValidateNullValue() {
        // Given
        final VXMaxValidator validator = new VXMaxValidator(10);
        // When
        Assertions.assertTrue(validator.isValid(null));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final VXMaxValidator validator = new VXMaxValidator(10);
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The value must be less than or equal to 10", errorMessage);
    }

    @Test
    void shouldReturnCorrectProperty() {
        // Given
        final VXMaxValidator validator = new VXMaxValidator(10);
        // When
        final var property = validator.getProperty();
        // Then
        Assertions.assertTrue(property.isPresent());
        Assertions.assertEquals("max", property.get().name());
        Assertions.assertEquals(Number.class, property.get().type());
    }
}

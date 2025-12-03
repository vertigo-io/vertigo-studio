package io.vertigo.vortex.model.types.validators.date;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXLocalDatePastValidatorTest {

    @Test
    void shouldValidatePastDate() {
        // Given
        final VXLocalDatePastValidator validator = new VXLocalDatePastValidator();
        final LocalDate value = LocalDate.now().minusDays(1);
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateFutureDate() {
        // Given
        final VXLocalDatePastValidator validator = new VXLocalDatePastValidator();
        final LocalDate value = LocalDate.now().plusDays(1);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldNotValidateToday() {
        // Given
        final VXLocalDatePastValidator validator = new VXLocalDatePastValidator();
        final LocalDate value = LocalDate.now();
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldValidateNullDate() {
        // Given
        final VXLocalDatePastValidator validator = new VXLocalDatePastValidator();
        final LocalDate value = null;
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final VXLocalDatePastValidator validator = new VXLocalDatePastValidator();
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The date must be in the past.", errorMessage);
    }
}

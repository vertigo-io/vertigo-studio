package io.vertigo.vortex.model.types.validators.date;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.gold.library.validators.date.VXLocalDateFutureValidator;

public class VXLocalDateFutureValidatorTest {

    @Test
    void shouldValidateFutureDate() {
        // Given
        final VXLocalDateFutureValidator validator = new VXLocalDateFutureValidator();
        final LocalDate value = LocalDate.now().plusDays(1);
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidatePastDate() {
        // Given
        final VXLocalDateFutureValidator validator = new VXLocalDateFutureValidator();
        final LocalDate value = LocalDate.now().minusDays(1);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldNotValidateToday() {
        // Given
        final VXLocalDateFutureValidator validator = new VXLocalDateFutureValidator();
        final LocalDate value = LocalDate.now();
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldValidateNullDate() {
        // Given
        final VXLocalDateFutureValidator validator = new VXLocalDateFutureValidator();
        final LocalDate value = null;
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final VXLocalDateFutureValidator validator = new VXLocalDateFutureValidator();
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The date must be in the future.", errorMessage);
    }
}

package io.vertigo.vortex.model.types.validators.time;

import java.time.Instant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.gold.library.validators.time.VXInstantFutureValidator;

public class VXInstantFutureValidatorTest {

    @Test
    void shouldValidateFutureInstant() {
        // Given
        final VXInstantFutureValidator validator = new VXInstantFutureValidator();
        final Instant value = Instant.now().plusSeconds(1);
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidatePastInstant() {
        // Given
        final VXInstantFutureValidator validator = new VXInstantFutureValidator();
        final Instant value = Instant.now().minusSeconds(1);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldNotValidateNow() {
        // Given
        final VXInstantFutureValidator validator = new VXInstantFutureValidator();
        final Instant value = Instant.now();
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldValidateNullInstant() {
        // Given
        final VXInstantFutureValidator validator = new VXInstantFutureValidator();
        final Instant value = null;
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final VXInstantFutureValidator validator = new VXInstantFutureValidator();
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The instant must be in the future.", errorMessage);
    }
}

package io.vertigo.vortex.model.types.validators.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXMinValidatorTest {

	@Test
	void shouldValidateIntegerGreaterThanMin() {
		// Given
		final VXMinValidator validator = new VXMinValidator(10);
		// When
		Assertions.assertTrue(validator.isValid(15));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateIntegerEqualToMin() {
		// Given
		final VXMinValidator validator = new VXMinValidator(10);
		// When
		Assertions.assertTrue(validator.isValid(10));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateIntegerLessThanMin() {
		// Given
		final VXMinValidator validator = new VXMinValidator(10);
		// When
		Assertions.assertFalse(validator.isValid(5));
	}

	@Test
	void shouldValidateDoubleGreaterThanMin() {
		// Given
		final VXMinValidator validator = new VXMinValidator(10.5);
		// When
		Assertions.assertTrue(validator.isValid(15.5));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateDoubleLessThanMin() {
		// Given
		final VXMinValidator validator = new VXMinValidator(10.5);
		// When
		Assertions.assertFalse(validator.isValid(5.5));
	}

	@Test
	void shouldValidateNullValue() {
		// Given
		final VXMinValidator validator = new VXMinValidator(10);
		// When
		Assertions.assertTrue(validator.isValid(null));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// Given
		final VXMinValidator validator = new VXMinValidator(10);
		// When
		final String errorMessage = validator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("The value must be greater than or equal to 10", errorMessage);
	}

	@Test
	void shouldReturnCorrectProperty() {
		// Given
		final VXMinValidator validator = new VXMinValidator(10);
		// When
		final var property = validator.getProperty();
		// Then
		Assertions.assertEquals("min", property.key().name());
		Assertions.assertEquals(10, property.value());
	}
}

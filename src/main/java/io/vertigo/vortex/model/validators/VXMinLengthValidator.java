package io.vertigo.vortex.model.validators;

import io.vertigo.vortex.model.VXValidator;

public record VXMinLengthValidator(int min) implements VXValidator {
}

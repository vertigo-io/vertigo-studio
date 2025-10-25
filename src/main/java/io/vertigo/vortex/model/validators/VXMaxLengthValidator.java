package io.vertigo.vortex.model.validators;

import io.vertigo.vortex.model.VXValidator;

public record VXMaxLengthValidator(int max) implements VXValidator {
}

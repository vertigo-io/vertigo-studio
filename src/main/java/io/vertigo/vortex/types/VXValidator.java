package io.vertigo.vortex.types;

import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;

/**
 * Represents a validator for a specific data type.
 * Validators are used within DomainTypes to enforce constraints.
 * @param <D> the data type of the value to validate
 * @synthetic
 */
public interface VXValidator<V, P> {
	/**
	 * Checks if the given value satisfies the constraint.
	 * @param value the value to validate
	 * @return true if the value is valid, false otherwise
	 */
	boolean isValid(V value);

	/**
	 * Returns the error message to be used when the constraint is not satisfied.
	 * @return the error message
	 */
	LocaleMessageText getErrorMessage();

	/**
	 * @return The property associated with this validator, if any.
	 */
	default Optional<VxProperty<P>> getProperty() {
		return Optional.empty();
	}
}

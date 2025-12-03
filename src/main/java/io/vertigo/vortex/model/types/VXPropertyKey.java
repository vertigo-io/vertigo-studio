package io.vertigo.vortex.model.types;

/**
 * Represents a key for a validator property, including its name and expected type.
 * This allows for more type-safe and descriptive property definitions.
 *
 * @param name The name of the property (e.g., "required", "minLength").
 * @param type The expected class type of the property's value (e.g., Boolean.class, Integer.class).
 * @param <T> The generic type of the property's value.
 */
public record VXPropertyKey<T>(String name, Class<T> type) {
	// No additional methods needed for a record class beyond what's generated.

	public VXProperty<T> build(T value) {
		return new VXProperty(this, value);
	}
}

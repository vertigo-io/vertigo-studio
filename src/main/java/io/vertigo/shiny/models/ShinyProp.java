package io.vertigo.shiny.models;

/**
 * ShinyProp is a record that represents a key-value pair used to define properties
 * for various Shiny models. These properties allow for dynamic configuration and
 * customization of components.
 */
public record ShinyProp(String key, String value) {

	/**
	 * Creates a new ShinyProp with a string value.
	 * @param key The key of the property.
	 * @param value The string value of the property.
	 * @return A new ShinyProp instance.
	 */
	public static ShinyProp of(final String key, final String value) {
		return new ShinyProp(key, value);
	}

	/**
	 * Creates a new ShinyProp with an integer value.
	 * @param key The key of the property.
	 * @param value The integer value of the property.
	 * @return A new ShinyProp instance.
	 */
	public static ShinyProp of(final String key, final int value) {
		return new ShinyProp(key, String.valueOf(value));
	}

	/**
	 * Creates a new ShinyProp with a boolean value.
	 * @param key The key of the property.
	 * @param value The boolean value of the property.
	 * @return A new ShinyProp instance.
	 */
	public static ShinyProp of(final String key, final boolean value) {
		return new ShinyProp(key, String.valueOf(value));
	}
}

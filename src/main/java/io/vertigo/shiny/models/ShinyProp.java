package io.vertigo.shiny.models;

public record ShinyProp(String key, String value) {

	public static ShinyProp of(final String key, final String value) {
		return new ShinyProp(key, value);
	}

	public static ShinyProp of(final String key, final int value) {
		return new ShinyProp(key, String.valueOf(value));
	}
}

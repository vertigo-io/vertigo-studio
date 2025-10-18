package io.vertigo.shiny.models;

public record ShinyProp(String key, String value) {

	public static ShinyProp of(String key, String value) {
		return new ShinyProp(key, value);
	}

	public static ShinyProp of(String key, int value) {
		return new ShinyProp(key, String.valueOf(value));
	}
}

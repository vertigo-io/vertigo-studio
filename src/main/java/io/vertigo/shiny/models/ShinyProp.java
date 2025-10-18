package io.vertigo.shiny.models;

public record ShinyProp(String name, String value) {

	public static ShinyProp of(String name, String value) {
		return new ShinyProp(name, value);
	}

	public static ShinyProp of(String name, int value) {
		return new ShinyProp(name, String.valueOf(value));
	}
}

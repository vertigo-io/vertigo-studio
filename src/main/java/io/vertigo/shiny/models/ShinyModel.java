package io.vertigo.shiny.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ShinyModel {
	@JsonProperty
	String shinyType();

	default List<ShinyProp> props() {
		return List.of();
	}
}

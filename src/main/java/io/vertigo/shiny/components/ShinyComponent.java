package io.vertigo.shiny.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ShinyComponent {
	@JsonProperty
	String type();
}

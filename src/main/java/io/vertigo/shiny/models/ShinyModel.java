package io.vertigo.shiny.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A model is the pure aspect of one of several views.
 * A model may have props (such as pagination for tables) to
 */
public interface ShinyModel {
	@JsonProperty
	default String shinyType() {
		return this.getClass().getSimpleName();
	}

	//required if statefull
	default UUID id() {
		if (state() != null) {
			throw new IllegalStateException("id is required when statefull");
		}
		return null;
	}

	default ShinyState state() {
		return null;
	}
}

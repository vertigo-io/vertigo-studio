package io.vertigo.shiny.renderers;

import io.vertigo.shiny.models.ShinyModel;

public interface ShinyComponentRenderer<S extends ShinyModel> {
	boolean accept(ShinyModel component);

	void render(S component);
}

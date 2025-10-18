package io.vertigo.shiny.renderers;

import io.vertigo.shiny.models.ShinyModel;

public interface ShinyModelRenderer<S extends ShinyModel> {
	boolean accept(ShinyModel model);

	void render(S model);
}

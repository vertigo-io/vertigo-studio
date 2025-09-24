package io.vertigo.shiny.renderers;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public interface ShinyComponentRenderer<S extends ShinyComponent, Y extends ShinyStyle<S>> {
	boolean accept(ShinyComponent component);

	void render(S component, Y style, ShinyWriter writer);
}

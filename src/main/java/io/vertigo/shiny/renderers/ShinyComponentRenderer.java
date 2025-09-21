package io.vertigo.shiny.renderers;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public interface ShinyComponentRenderer<S extends ShinyComponent> {
    boolean accept(ShinyComponent component);
    void render(S component, ShinyWriter writer);
}

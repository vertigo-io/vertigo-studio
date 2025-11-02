package io.vertigo.shiny.models;

import java.util.List;

public interface ShinyLayout extends ShinyModel {
    List<ShinyModel> getContent(); // A layout contains other blocks or layouts
}

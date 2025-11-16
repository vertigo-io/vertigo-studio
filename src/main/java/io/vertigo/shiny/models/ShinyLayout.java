package io.vertigo.shiny.models;

import java.util.List;

public interface ShinyLayout extends ShinyModel {
	List<ShinyBlock> content();
}

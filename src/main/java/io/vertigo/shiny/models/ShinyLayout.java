package io.vertigo.shiny.models;

import java.util.List;

/**
 * ShinyLayout is an interface representing a component that defines the structure and arrangement
 * of ShinyBlocks within the Shiny framework. It extends ShinyModel and typically contains a list
 * of ShinyBlock objects that form the layout's content.
 */
public interface ShinyLayout extends ShinyModel {
	List<ShinyBlock> content();
}

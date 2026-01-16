package io.vertigo.shiny.models.block;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

/**
 * ShinyContainer is a record representing a block that can contain a list of other Shiny models.
 * It implements the ShinyBlock interface, allowing it to be used within layouts.
 *
 * @param id The unique identifier for this container.
 * @param content The list of ShinyModel objects contained within this container.
 */
public record ShinyContainer(
		@Nonnull UUID id,
		List<ShinyModel> content) implements ShinyBlock {

	/**
	 * Constructor for ShinyContainer.
	 * Asserts that the ID is not null.
	 */
	public ShinyContainer {
		Assertion.check().isNotNull(id);
	}
}

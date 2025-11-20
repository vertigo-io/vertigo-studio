package io.vertigo.shiny.models.data.card;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyCard(
		@Nonnull UUID id,
		@Nonnull String title,
		String subtitle,
		String description,
		String imageUrl,
		String imageAlt,
		String link,
		String icon,
		String badgeLabel,
		String badgeColor,
		ShinyCardFormat format) implements ShinyBlock {

	public ShinyCard {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotBlank(title, "Card title cannot be blank");
	}
}

package io.vertigo.shiny.models.data.card;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyCard(
		UUID id,
		String title,
		String subtitle,
		String description,
		String imageUrl,
		String imageAlt,
		String link,
		String icon,
		String badgeLabel,
		String badgeColor,
		ShinyCardFormat format) implements ShinyModel {

	public ShinyCard {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotBlank(title, "Card title cannot be blank");
	}
}

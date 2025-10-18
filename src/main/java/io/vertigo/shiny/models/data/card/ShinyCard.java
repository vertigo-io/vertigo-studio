package io.vertigo.shiny.models.data.card;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyCard(
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

	@Override
	public String shinyType() {
		return "ShinyCard";
	}

	public ShinyCard {
		Assertion.check()
				.isNotBlank(title, "Card title cannot be blank");
	}
}

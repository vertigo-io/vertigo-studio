package io.vertigo.shiny.components.data.card;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

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
		ShinyCardFormat format) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyCard";
	}

	public ShinyCard {
		Assertion.check()
				.isNotBlank(title, "Card title cannot be blank");
	}
}

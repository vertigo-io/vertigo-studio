package io.vertigo.shiny.components.card;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyCardComponent(
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

	public ShinyCardComponent {
		Assertion.check()
				.isNotBlank(title, "Card title cannot be blank");
	}
}

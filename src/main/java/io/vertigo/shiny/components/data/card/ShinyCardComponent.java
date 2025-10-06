package io.vertigo.shiny.components.data.card;

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

	@Override
	public String type() {
		return "card";
	}


	public ShinyCardComponent {
		Assertion.check()
				.isNotBlank(title, "Card title cannot be blank");
	}
}

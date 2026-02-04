package io.vertigo.banshee.samples;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.text.chip.ShinyChipVariant;
import io.vertigo.shiny.models.text.rating.ShinyRatingScale;
import io.vertigo.shiny.models.text.status.ShinyStatusType;

final class TextSamples {
	static ShinyModel figlet() {
		return Shiny.figlet()
				.withText("Hello Vertigo")
				.build();
	}

	static ShinyModel textpath() {
		return Shiny.textPath()
				.withPath("root/node/leaf")
				.withSeparator("/")
				.build();
	}

	static ShinyModel textpath2() {
		return Shiny.textPath()
				.withPath("root/node/leaf")
				.withSeparator("/")
				.build();
	}

	static ShinyModel title() {
		return Shiny.title()
				.withText("This is a title")
				.withLevel(2)
				.build();
	}

	static ShinyModel paragraph() {
		return Shiny.paragraph()
				.withText("This is a paragraph.")
				.build();
	}

	static ShinyModel rating() {
		return Shiny.rating()
				.withLabel("User satisfaction")
				.withValue(3.5)
				.withScale(ShinyRatingScale.SCALE_5)
				.withAllowHalfRating(true)
				.build();
	}

	static ShinyModel status() {
		return Shiny.status()
				.withTitle("Component Status")
				.addType(ShinyStatusType.SUCCESS)
				.addType(ShinyStatusType.ERROR)
				.addType(ShinyStatusType.WARNING)
				.build();
	}

	static ShinyModel sparkline() {
		return Shiny.sparkline()
				.withTitle("S.p.a.r.k.l.i.n.e")
				.withValues(156, 450, 300, 200, 100, 23)
				.build();
	}

	static ShinyModel chip() {
		return Shiny.chip()
				.withText("Vuetify")
				.withColor("red")
				.withVariant(ShinyChipVariant.ELEVATED)
				.withIcon("mdi-vuetify")
				.withClosable(true)
				.build();
	}

}

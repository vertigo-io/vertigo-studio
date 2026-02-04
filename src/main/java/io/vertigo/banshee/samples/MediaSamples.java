package io.vertigo.banshee.samples;

import io.vertigo.banshee.com.BansheeCommandLine;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;

final class MediaSamples {

	static ShinyModel rss(BansheeCommandLine commandLine) throws Exception {
		return switch (commandLine.args().toLowerCase()) {
			case "monde", "lemonde", "le monde" -> leMonde();
			case "bbc" -> bbc();
			case "france info", "franceinfo" -> franceInfo();
			default -> throw new IllegalArgumentException("Unexpected value: " + commandLine.args());
		};
	}

	private static ShinyModel leMonde() {
		return Shiny.rss()
				.withFeed("https://www.lemonde.fr/rss/une.xml")
				.build();
	}

	private static ShinyModel bbc() {
		return Shiny.rss()
				.withFeed("https://feeds.bbci.co.uk/news/world/rss.xml")
				.build();
	}

	private static ShinyModel franceInfo() {
		return Shiny.rss()
				.withFeed("https://www.francetvinfo.fr/titres.rss")
				.build();
	}

	static ShinyModel image() {
		return Shiny.image()
				.withTitle("Random image from picsum")
				.withUrl("https://picsum.photos/800/600")
				.withAlt("Random image from picsum")
				.build();
	}

	static ShinyModel youtube() {
		return Shiny.youtube()
				.withTitle("Rick Astley - Never Gonna Give You Up")
				.withVideoId("dQw4w9WgXcQ")
				.build();
	}

	static ShinyModel slides() {
		final String markdown = """
				             # Slide 1
				             Hello

				             ---
				             # Slide 2
				             World
				""";
		return Shiny.slide()
				.withMarkdown(markdown)
				.build();
	}

	static ShinyModel pdf() {
		return Shiny.pdf()
				.withTitle("Arthur Rimbaud - Poèmes")
				.withPath("sample-report.pdf").build();
	}
}

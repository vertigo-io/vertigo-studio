package io.vertigo.banshee.samples;

import io.vertigo.banshee.com.BansheeCommandExecutor;
import io.vertigo.banshee.com.BansheeCommandLine;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;

final class RssCommandExecutor implements BansheeCommandExecutor {

	public ShinyModel execute(BansheeCommandLine commandLine) throws Exception {
		return switch (commandLine.args().toLowerCase()) {
			case "monde", "lemonde", "le monde" -> leMonde();
			case "bbc" -> bbc();
			case "france info", "franceinfo" -> franceInfo();
			default -> throw new IllegalArgumentException("Unexpected value: " + commandLine.args());
		};
	}

	private ShinyModel leMonde() {
		return Shiny.rss()
				.withFeed("https://www.lemonde.fr/rss/une.xml")
				.build();
	}

	private ShinyModel bbc() {
		return Shiny.rss()
				.withFeed("https://feeds.bbci.co.uk/news/world/rss.xml")
				.build();
	}

	private ShinyModel franceInfo() {
		return Shiny.rss()
				.withFeed("https://www.francetvinfo.fr/titres.rss")
				.build();
	}
}

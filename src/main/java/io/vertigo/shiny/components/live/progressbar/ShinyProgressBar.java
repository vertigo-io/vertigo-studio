package io.vertigo.shiny.components.live.progressbar;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.live.ShinyLiveComponent;

public final class ShinyProgressBar extends ShinyLiveComponent<ShinyProgressBar> {
	private int total = 0; // Valeur totale correspondant à 100%
	private volatile int currentProgress;
	private ShinyProgressBarStyle progressBarStyle;

	// Constructeur
	public ShinyProgressBar() {
		super();
		this.progressBarStyle = Shiny.theme().progressBarStyle();
	}

	public ShinyProgressBar withStyle(final ShinyProgressBarStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.progressBarStyle = style;
		return this;
	}

	//  Valeur totale correspondant à 100%
	public ShinyProgressBar withTotal(final int value) {
		Assertion.check().isTrue(value > 0, "total must be > 0");
		//---
		this.total = value;
		return this;
	}

	// Méthode pour mettre à jour la progression
	public void liveUpdate(final int progress) {
		if (progress < 0) {
			this.currentProgress = 0;
		} else if (progress > total) {
			this.currentProgress = 100;
		} else {
			this.currentProgress = progress;
		}
	}

	synchronized protected void draw(ShinyWriter writer) {
		// Calculer le pourcentage
		final int percentage = (currentProgress * 100) / total;
		// Calculer le nombre de carrés à remplir
		final int filled = (currentProgress * progressBarStyle.maxLength()) / total;

		// Construire la barre
		writer.print("\r")
				.print("[")
				.print("█".repeat(filled))
				.print("▒".repeat(progressBarStyle.maxLength() - filled))
				.print("] ")
				.print(percentage + "%")
				.flush(); //On force le flush
	}

}
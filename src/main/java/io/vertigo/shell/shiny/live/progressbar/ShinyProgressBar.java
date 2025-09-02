package io.vertigo.shell.shiny.live.progressbar;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.live.ShinyLiveComponent;

public final class ShinyProgressBar extends ShinyLiveComponent<ShinyProgressBar> {
	private final Shiny shiny;
	private int total = 0; // Valeur totale correspondant à 100%
	private int barLength; // Longueur de la barre en caractères
	private volatile int currentProgress;

	// Constructeur
	public ShinyProgressBar(final Shiny shiny) {
		super(shiny);
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
		this.barLength = 50;
	}

	public ShinyProgressBar length(final int length) {
		this.barLength = length;
		return this;
	}

	//  Valeur totale correspondant à 100%
	public ShinyProgressBar total(final int value) {
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

	synchronized protected void draw() {
		// Calculer le pourcentage
		final int percentage = (currentProgress * 100) / total;
		// Calculer le nombre de carrés à remplir
		final int filled = (currentProgress * barLength) / total;

		// Construire la barre
		final StringBuilder bar = new StringBuilder("[")
				.append("█".repeat(filled))
				.append("▒".repeat(barLength - filled))
				.append("] ")
				.append(percentage).append("%");
		// Afficher la barre
		shiny.getWriter().print("\r" + bar);
		shiny.getWriter().flush(); //On force le flush
	}

}

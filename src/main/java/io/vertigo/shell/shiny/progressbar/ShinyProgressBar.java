package io.vertigo.shell.shiny.progressbar;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;

public final class ShinyProgressBar {
	private final Shiny shiny;
	private int total = 0; // Valeur totale correspondant à 100%
	private int barLength; // Longueur de la barre en caractères
	private int progress; // Progression actuelle

	// Constructeur
	public ShinyProgressBar(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
		this.barLength = 50;
		this.progress = 0;
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
	public void setProgress(final int progress) {
		if (progress >= 0 && progress <= total) {
			this.progress = progress;
		}
	}

	// Méthode pour afficher la barre de progression
	public void print() {
		// Calculer le pourcentage
		final int percentage = (progress * 100) / total;
		// Calculer le nombre de carrés à remplir
		final int filled = (progress * barLength) / total;

		// Construire la barre
		final StringBuilder bar = new StringBuilder("[")
				.append("█".repeat(filled))
				.append("▒".repeat(barLength - filled))
				.append("] ")
				.append(percentage).append("%");
		// Afficher la barre
		shiny.getWriter().print("\r\n" + bar);
		shiny.getWriter().flush(); //On force le flush
	}

	// Méthode pour finaliser l'affichage
	public void finish() {
		shiny.getWriter().println();
		shiny.getWriter().flush(); //On force le flush
		//System.out.println("Tâche terminée !");
	}
}

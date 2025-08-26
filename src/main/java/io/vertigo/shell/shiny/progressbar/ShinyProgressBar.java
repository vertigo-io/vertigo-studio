package io.vertigo.shell.shiny.progressbar;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;

public final class ShinyProgressBar {
	private int total = 0; // Valeur totale correspondant à 100%
	private final int barLength; // Longueur de la barre en caractères
	private int progress; // Progression actuelle

	// Constructeur
	public ShinyProgressBar(Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.barLength = 50; // Longueur fixe
		this.progress = 0;
	}

	//  Valeur totale correspondant à 100%
	public ShinyProgressBar total(int total) {
		Assertion.check().isTrue(total > 0, "total must be > 0");
		//---
		this.total = total;
		return this;
	}

	// Méthode pour mettre à jour la progression
	public void setProgress(int progress) {
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
		final StringBuilder bar = new StringBuilder("[");
		for (int i = 0; i < barLength; i++) {
			bar.append(i < filled ? "█" : "▒");
		}
		bar.append("] ").append(percentage).append("%");
		// Afficher la barre
		System.out.print("\r" + bar.toString());
	}

	// Méthode pour finaliser l'affichage
	public void finish() {
		System.out.println();
		//System.out.println("Tâche terminée !");
	}
}

package io.vertigo.shell.shiny;

public final class ShinyProgressBar {
	private final int total; // Valeur totale correspondant à 100%
	private final int barLength; // Longueur de la barre en caractères
	private int progress; // Progression actuelle

	// Constructeur
	public ShinyProgressBar(int total) {
		this.total = total;
		this.barLength = 50; // Longueur fixe
		this.progress = 0;
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
			bar.append(i < filled ? "\u001B[42m█\u001B[0m" : "\u001B[44m▒\u001B[0m");
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

	// Exemple d'utilisation
	public static void main(String[] args) throws InterruptedException {
		ShinyProgressBar progressBar = new ShinyProgressBar(100);
		for (int i = 0; i <= 100; i++) {
			progressBar.setProgress(i);
			progressBar.print();
			Thread.sleep(100);
		}
		progressBar.finish();
	}
}

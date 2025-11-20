package io.vertigo.banshee.samples;

import io.vertigo.banshee.com.BansheeCommand;
import io.vertigo.banshee.com.BansheeCommandExecutor;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;

final class ChartSamples {
	static class BarSample implements BansheeCommandExecutor {
		public ShinyModel execute(BansheeCommand command) {
			return Shiny.barChart()
					.withTitle("Ventes par produit")
					.withLabels("telephones", "ordinateurs", "livres")
					.addSerie("Ventes 2023", 156.0, 34.0, 55.0)
					.addSerie("Ventes 2024", 180.0, 45.0, 65.0)
					.build();
		}
	}

	static class PieSample implements BansheeCommandExecutor {
		public ShinyModel execute(BansheeCommand command) {
			return Shiny.pieChart()
					.withTitle("Répartition des ventes")
					.withLabels("Téléphones", "Ordinateurs", "Livres")
					.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
					.build();
		}
	}

	static class PieSample2 implements BansheeCommandExecutor {
		public ShinyModel execute(BansheeCommand command) {
			return Shiny.pieChart()
					.withTitle("Répartition des ventes")
					.withLabels("Téléphones", "Ordinateurs", "Livres")
					.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
					.addSerie("Ventes 2024", 45.0, 18.0, 16.0)
					.addSerie("Ventes 2025", 44.0, 16.0, 17.0)
					.build();
		}
	}

	static class DonutSample implements BansheeCommandExecutor {
		public ShinyModel execute(BansheeCommand command) {
			return Shiny.donutChart()
					.withTitle("Répartition des ventes")
					.withLabels("Téléphones", "Ordinateurs", "Livres")
					.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
					.addSerie("Ventes 2024", 45.0, 18.0, 16.0)
					.addSerie("Ventes 2025", 44.0, 16.0, 17.0)
					.build();
		}
	}

	static class AreaSample implements BansheeCommandExecutor {
		public ShinyModel execute(BansheeCommand command) {
			return Shiny.areaChart()
					.withTitle("Ventes par mois")
					.withLabels("Jan", "Fev", "Mar", "Avr", "Mai", "Juin")
					.addSerie("Ventes 2023", 120.0, 150.0, 170.0, 200.0, 220.0, 240.0)
					.addSerie("Ventes 2024", 130.0, 160.0, 180.0, 210.0, 230.0, 250.0)
					.build();
		}
	}

	static class LineSample implements BansheeCommandExecutor {
		public ShinyModel execute(BansheeCommand command) {
			return Shiny.lineChart()
					.withTitle("Ventes par mois")
					.withLabels("Jan", "Fev", "Mar", "Avr", "Mai", "Juin")
					.addSerie("Ventes 2023", 120.0, 150.0, 170.0, 200.0, 220.0, 240.0)
					.addSerie("Ventes 2024", 130.0, 160.0, 180.0, 210.0, 230.0, 250.0)
					.build();
		}
	}

	static class RadarSample implements BansheeCommandExecutor {
		public ShinyModel execute(BansheeCommand command) {
			return Shiny.radarChart()
					.withTitle("Final Fantasy VII Stats")
					.withLabels("Attack", "Defense", "Magic Attack", "Magic Defense", "Speed", "Luck")
					.addSerie("Cloud", 85.0, 70.0, 80.0, 60.0, 90.0, 75.0)
					.addSerie("Sephiroth", 95.0, 80.0, 98.0, 85.0, 92.0, 88.0)
					.build();
		}
	}
}

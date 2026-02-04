package io.vertigo.banshee.samples;

import io.vertigo.banshee.com.BansheeCommandLine;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.dataviz.chart.ShinyChart;
import io.vertigo.shiny.models.dataviz.flow.NodeType;
import io.vertigo.shiny.models.dataviz.flow.ShinyFlowBuilder;
import io.vertigo.shiny.models.dataviz.geomap.ShinyGeoPoint;
import io.vertigo.shiny.models.dataviz.mindmap.ShinyMindMapNodeBuilder;

final class DatavizSamples {
	static ShinyChart barSample() {
		return Shiny.barChart()
				.withTitle("Ventes par produit")
				.withLabels("telephones", "ordinateurs", "livres")
				.addSerie("Ventes 2023", 156.0, 34.0, 55.0)
				.addSerie("Ventes 2024", 180.0, 45.0, 65.0)
				.build();
	}

	static ShinyChart pieSample() {
		return Shiny.pieChart()
				.withTitle("Répartition des ventes")
				.withLabels("Téléphones", "Ordinateurs", "Livres")
				.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
				.build();
	}

	static ShinyChart pieSample2() {
		return Shiny.pieChart()
				.withTitle("Répartition des ventes")
				.withLabels("Téléphones", "Ordinateurs", "Livres")
				.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
				.addSerie("Ventes 2024", 45.0, 18.0, 16.0)
				.addSerie("Ventes 2025", 44.0, 16.0, 17.0)
				.build();
	}

	static ShinyChart donutSample(BansheeCommandLine command) {
		return Shiny.donutChart()
				.withTitle("Répartition des ventes")
				.withLabels("Téléphones", "Ordinateurs", "Livres")
				.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
				.addSerie("Ventes 2024", 45.0, 18.0, 16.0)
				.addSerie("Ventes 2025", 44.0, 16.0, 17.0)
				.build();
	}

	static ShinyChart areaSample() {
		return Shiny.areaChart()
				.withTitle("Ventes par mois")
				.withLabels("Jan", "Fev", "Mar", "Avr", "Mai", "Juin")
				.addSerie("Ventes 2023", 120.0, 150.0, 170.0, 200.0, 220.0, 240.0)
				.addSerie("Ventes 2024", 130.0, 160.0, 180.0, 210.0, 230.0, 250.0)
				.build();
	}

	static ShinyChart lineSample() {
		return Shiny.lineChart()
				.withTitle("Ventes par mois")
				.withLabels("Jan", "Fev", "Mar", "Avr", "Mai", "Juin")
				.addSerie("Ventes 2023", 120.0, 150.0, 170.0, 200.0, 220.0, 240.0)
				.addSerie("Ventes 2024", 130.0, 160.0, 180.0, 210.0, 230.0, 250.0)
				.build();
	}

	static ShinyChart radarSample() {
		return Shiny.radarChart()
				.withTitle("Final Fantasy VII Stats")
				.withLabels("Attack", "Defense", "Magic Attack", "Magic Defense", "Speed", "Luck")
				.addSerie("Cloud", 85.0, 70.0, 80.0, 60.0, 90.0, 75.0)
				.addSerie("Sephiroth", 95.0, 80.0, 98.0, 85.0, 92.0, 88.0)
				.build();
	}

	static ShinyModel flow() {
		return new ShinyFlowBuilder()
				.withNode("1", "Order Received", 100, 50, NodeType.RR)
				.withNode("2", "Payment Processed", 300, 50, NodeType.LR)
				.withNode("3", "Items Shipped", 500, 50, NodeType.TB)
				.withNode("4", "Invoice Generated", 300, 200, NodeType.LR)
				.withNode("5", "Billing Completed", 500, 200, NodeType.LL)
				.withEdge("e1-2", "1", "2", "Process Payment")
				.withEdge("e2-3", "2", "3", "Ship Items")
				.withEdge("e2-4", "2", "4", "Generate Invoice")
				.withEdge("e4-5", "4", "5", "Finalize Billing")
				.build();
	}

	static ShinyModel gauge() {
		return Shiny.gauge()
				.withTitle("Ventes par produit")
				.withValue(156)
				.withMaxValue(450)
				.build();
	}

	static ShinyModel map() {
		return Shiny.geoMap()
				.withTitle("Tour Eiffel & Saint Germain")
				.addGeoPoint(ShinyGeoPoint.of(48.8584, 2.2945, "Tour Eiffel"))
				.addGeoPoint(ShinyGeoPoint.of(48.901022, 2.100765, "Saint Germain en Laye"))
				.build();
	}

	static ShinyModel mindmap() {
		return Shiny.mindMap()
				.withTitle("Mouvements Artistiques")
				.withRootNode(
						new ShinyMindMapNodeBuilder("root", "Mouvements Artistiques")
								.addAllChildren(
										new ShinyMindMapNodeBuilder("classicisme", "Classicisme")
												.withDirection("left")
												.addAllChildren(
														new ShinyMindMapNodeBuilder("renaissance", "Renaissance").build(),
														new ShinyMindMapNodeBuilder("baroque", "Baroque").build())
												.build(),
										new ShinyMindMapNodeBuilder("romantisme", "Romantisme")
												.withDirection("right")
												.addAllChildren(
														new ShinyMindMapNodeBuilder("pre_raphaelite", "Préraphaélisme").build(),
														new ShinyMindMapNodeBuilder("symbolisme", "Symbolisme").build())
												.build(),
										new ShinyMindMapNodeBuilder("moderne", "Art Moderne")
												.withDirection("left")
												.addAllChildren(
														new ShinyMindMapNodeBuilder("impressionnisme", "Impressionnisme").build(), new ShinyMindMapNodeBuilder("cubisme", "Cubisme").build())
												.build(),
										new ShinyMindMapNodeBuilder("contemporain", "Art Contemporain").withDirection("right").addAllChildren(new ShinyMindMapNodeBuilder("pop_art", "Pop Art").build(),
												new ShinyMindMapNodeBuilder("minimalisme", "Minimalisme").build())
												.build())
								.build())
				.build();
	}

	static ShinyModel organization() {
		return Shiny.organization()
				.addNode("1", null, "John Doe", "CEO", "https://randomuser.me/api/portraits/men/1.jpg")
				.addNode("2", "1", "Jane Smith", "CTO", "https://randomuser.me/api/portraits/women/2.jpg")
				.addNode("3", "1", "Mike Johnson", "CFO", "https://randomuser.me/api/portraits/men/3.jpg")
				.addNode("4", "2", "Emily Brown", "Lead Developer", "https://randomuser.me/api/portraits/women/4.jpg")
				.addNode("5", "2", "David Wilson", "DevOps Engineer", "https://randomuser.me/api/portraits/men/5.jpg")
				.addNode("6", "3", "Sarah Davis", "Accountant", "https://randomuser.me/api/portraits/women/6.jpg")
				.build();
	}

	static ShinyModel sankey() {
		return Shiny.sankey()
				.withTitle("Flux d\'énergie")
				.addLink("Nucléaire", "Réseau électrique", 120.0)
				.addLink("Hydraulique", "Réseau électrique", 80.0)
				.addLink("Éolien", "Réseau électrique", 60.0)
				.addLink("Solaire", "Réseau électrique", 40.0)
				.addLink("Charbon", "Réseau électrique", 100.0)
				.addLink("Réseau électrique", "Industrie", 150.0)
				.addLink("Réseau électrique", "Transport", 70.0)
				.addLink("Réseau électrique", "Résidentiel", 100.0)
				.addLink("Réseau électrique", "Pertes réseau", 20.0)
				.addLink("Résidentiel", "Chauffage", 40.0)
				.addLink("Résidentiel", "Électroménager", 30.0)
				.addLink("Résidentiel", "Informatique", 30.0)
				.build();
	}

	static ShinyModel timeline() {
		return Shiny.timeline()
				.withTitle("Project Timeline")
				.addItem("Step 1: Conception", "Defining project goals and scope.", "blue", "mdi-lightbulb-on-outline")
				.addItem("Step 2: Development", "Building the core features.", "green", "mdi-code-braces")
				.addItem("Step 3: Testing", "Ensuring quality and stability.", "orange", "mdi-flask-empty-outline")
				.addItem("Step 4: Deployment", "Releasing to production.", "purple", "mdi-rocket-launch-outline")
				.build();
	}
}

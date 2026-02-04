package io.vertigo.banshee.samples;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.vertigo.banshee.com.BansheeCommandLine;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.card.ShinyCardFormat;
import io.vertigo.shiny.models.data.list.ShinyListType;
import io.vertigo.shiny.models.data.table.ShinyTableBuilder;
import io.vertigo.shiny.models.data.table.ShinyTableStateBuilder;
import io.vertigo.shiny.models.data.table.cell.ShinyAvatarCell;
import io.vertigo.shiny.models.data.table.cell.ShinyBadgeCell;
import io.vertigo.shiny.models.data.table.cell.ShinyButtonCell;
import io.vertigo.shiny.models.data.table.cell.ShinyChipCell;
import io.vertigo.shiny.models.data.table.cell.ShinyIconCell;
import io.vertigo.shiny.models.data.table.cell.ShinyProgressBarCell;
import io.vertigo.shiny.models.data.table.cell.ShinyRatingCell;
import io.vertigo.shiny.models.data.table.cell.ShinyStringCell;
import io.vertigo.shiny.models.text.chip.ShinyChipVariant;
import io.vertigo.shiny.models.text.rating.ShinyRatingScale;

final class DataSamples {
	static ShinyModel card() {
		return Shiny.card()
				.withTitle("Mon Titre de Carte")
				.withSubtitle("Un sous-titre pour le contexte").withDescription("Ceci est le contenu principal de ma carte. Il peut être plus long et contenir des informations détaillées sur le sujet de la carte.").withImageUrl("https://picsum.photos/id/237/200/300").withImageAlt("Image aléatoire de Picsum").withLink("https://www.vertigo.io").withIcon("star").withBadge("Nouveau", "blue").withFormat(ShinyCardFormat.M).build();
	}

	static ShinyModel datagrid() {
		return Shiny.dataGrid().withTitle("My DataGrid")
				.addColumn("ID", "id", true, true)
				.addColumn("Name", "name", true, false)
				.addColumn("Age", "age", true, false)
				.withData(List.of(
						Map.of("id", 1, "name", "John", "age", 30),
						Map.of("id", 2, "name", "Jane", "age", 25),
						Map.of("id", 3, "name", "Doe", "age", 40)))
				.build();
	}

	static ShinyModel json() {
		return Shiny.json()
				.withJson(
						"""
								{
								"title": "The Shining",
								"director": "Stanley Kubrick",
								"release_year": 1980,
								"genre": ["Horror", "Thriller"],
								"duration": "2h 26m",
								"cast": ["Jack Nicholson", "Shelley Duvall", "Danny Lloyd"],
								"synopsis": "A family heads to an isolated hotel for the winter."
								}
								""")
				.withTitle("Fiche de Shinning")
				.build();
	}

	static ShinyModel list() {
		return Shiny.list().withTitle("planetes")
				.withType(ShinyListType.UNORDERED).addItem("Uranus").addList(Shiny.list().withTitle("Mars").withType(ShinyListType.UNORDERED).addItem("Bleue").addItem("Rouge").addItem("Verte").build()).addItem("Saturn").addItem("Venus").build();
	}

	static ShinyModel table() {
		return Shiny.table()
				.withTitle("carnet d'adresses")
				.withNoDataFound("no files found")
				.withHeader("Prénom", "Nom")
				.addRow("Arthur", "Penn")
				.addRow("Marilyn", "Pinson")
				.build();
	}

	static ShinyModel table2(BansheeCommandLine cmd) {
		final ShinyTableBuilder tableBuilder = Shiny.table()
				.withTitle("alphabet")
				.withHeader("Lettre de A à Z");
		final String page = cmd.state() == null
				? "1"
				: cmd.state().getValue("page")
						.orElseGet(() -> "1");

		switch (page) {
			case "1":
				tableBuilder.addRow("A").addRow("B").addRow("C").addRow("D").addRow("E");
				break;
			case "2":
				tableBuilder.addRow("F").addRow("G").addRow("H").addRow("I").addRow("J");
				break;
			case "3":
				tableBuilder.addRow("K").addRow("L").addRow("M").addRow("N").addRow("O");
				break;
			case "4":
				tableBuilder.addRow("P").addRow("Q").addRow("R").addRow("S").addRow("T");
				break;
			case "5":
				tableBuilder.addRow("U").addRow("V").addRow("W").addRow("X").addRow("Y");
				break;
			case "6":
				tableBuilder.addRow("Z");
				break;
			default:
				tableBuilder.withNoDataFound("No data Found");
				break;
		}
		final UUID id = cmd.id() != null
				? cmd.id()
				: UUID.randomUUID();
		return tableBuilder
				.withId(id)
				.withState(new ShinyTableStateBuilder()
						.withSortColumn(0)
						.withPageCount(6)
						.withPage(Integer.valueOf(page).intValue())
						.build())
				.build();
	}

	static ShinyModel table3() {
		return Shiny.table()
				.withTitle("Rich Content Table")
				.withHeader("Name", "Status", "Progress", "Rating", "Action", "Avatar", "Icon", "Badge")
				.addRow(new ShinyStringCell(UUID.randomUUID(), "Alice"),
						new ShinyChipCell(UUID.randomUUID(), "Active", "green", ShinyChipVariant.FLAT, false, null),
						new ShinyProgressBarCell(UUID.randomUUID(), 75, 100, "blue"),
						new ShinyRatingCell(UUID.randomUUID(), 4.5, ShinyRatingScale.SCALE_5, true),
						new ShinyButtonCell(UUID.randomUUID(), "View", "primary", "viewAlice"),
						new ShinyAvatarCell(UUID.randomUUID(), "https://randomuser.me/api/portraits/women/1.jpg", "Alice", "36px"),
						new ShinyIconCell(UUID.randomUUID(), "mdi-check-circle", "green", "24px"),
						new ShinyBadgeCell(UUID.randomUUID(), "New", "red"))
				.addRow(
						new ShinyStringCell(UUID.randomUUID(), "Bob"),
						new ShinyChipCell(UUID.randomUUID(), "Inactive", "red", ShinyChipVariant.OUTLINED, false, null),
						new ShinyProgressBarCell(UUID.randomUUID(), 25, 100, "red"),
						new ShinyRatingCell(UUID.randomUUID(), 2.0, ShinyRatingScale.SCALE_5, false),
						new ShinyButtonCell(UUID.randomUUID(), "Edit", "secondary", "editBob"),
						new ShinyAvatarCell(UUID.randomUUID(), "https://randomuser.me/api/portraits/men/2.jpg", "Bob", "36px"),
						new ShinyIconCell(UUID.randomUUID(), "mdi-alert-circle", "orange", "24px"),
						new ShinyBadgeCell(UUID.randomUUID(), "Urgent", "orange"))
				.addRow(
						new ShinyStringCell(UUID.randomUUID(), "Charlie"),
						new ShinyChipCell(UUID.randomUUID(), "Pending", "orange", ShinyChipVariant.ELEVATED, false, null),
						new ShinyProgressBarCell(UUID.randomUUID(), 50, 100, "orange"),
						new ShinyRatingCell(UUID.randomUUID(), 3.0, ShinyRatingScale.SCALE_5, true),
						new ShinyButtonCell(UUID.randomUUID(), "Delete", "error", "deleteCharlie"),
						new ShinyAvatarCell(UUID.randomUUID(), "https://randomuser.me/api/portraits/men/3.jpg", "Charlie", "36px"),
						new ShinyIconCell(UUID.randomUUID(), "mdi-information", "blue", "24px"),
						new ShinyBadgeCell(UUID.randomUUID(), "Info", "blue"))
				.build();
	}

	static ShinyModel tree() {
		return Shiny.tree()
				.withLabel("my directory")
				.addTree("Files").addLeaf("src")
				.addTree("main").addLeaf("file.txt").up().up()
				.addTree("test").addLeaf("testFile.txt").root().build();
	}
}

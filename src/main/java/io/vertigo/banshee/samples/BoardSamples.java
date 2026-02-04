package io.vertigo.banshee.samples;

import io.vertigo.banshee.com.BansheeCommandLine;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.board.ShinyBoardCard;
import io.vertigo.shiny.models.data.board.ShinyBoardList;

final class BoardSamples {

	static ShinyModel crm(BansheeCommandLine command) {
		final ShinyBoardCard card1 = Shiny.boardCard()
				.withTitle("Évaluation des besoins")
				.withDescription("Rencontrer les équipes pour comprendre les attentes et les processus actuels.")
				.build();

		final ShinyBoardCard card2 = Shiny.boardCard()
				.withTitle("Sélection du CRM")
				.withDescription("Rechercher et choisir la solution CRM la plus adaptée (Salesforce, HubSpot, Zoho CRM, etc.).")
				.build();

		final ShinyBoardCard card3 = Shiny.boardCard()
				.withTitle("Planification de l'intégration")
				.withDescription("Définir les étapes clés, les ressources nécessaires et le calendrier.")
				.build();

		final ShinyBoardList todoList = Shiny.boardList()
				.withName("À faire")
				.withPosition(1)
				.withColor("#424242") // Dark grey
				.addCard(card1)
				.addCard(card2)
				.addCard(card3)
				.build();

		final ShinyBoardCard card4 = Shiny.boardCard()
				.withTitle("Configuration initiale")
				.withDescription("Paramétrer les utilisateurs, les rôles et les permissions.")
				.build();

		final ShinyBoardCard card5 = Shiny.boardCard()
				.withTitle("Importation des données existantes")
				.withDescription("Migrer les contacts, opportunités et historiques clients.")
				.build();

		final ShinyBoardList inProgressList = Shiny.boardList()
				.withName("En cours")
				.withPosition(2)
				.withColor("#212121") // Even darker grey
				.addCard(card4)
				.addCard(card5)
				.build();

		final ShinyBoardCard card6 = Shiny.boardCard()
				.withTitle("Formation des utilisateurs")
				.withDescription("Organiser des sessions de formation pour les équipes commerciales et marketing.")
				.build();

		final ShinyBoardList doneList = Shiny.boardList()
				.withName("Terminé")
				.withPosition(3)
				.withColor("#000000") // Black
				.addCard(card6)
				.build();

		return Shiny.board()
				.withName("Installation CRM")
				.withDescription("Tableau de bord pour le suivi de l'installation du nouveau CRM.")
				.withBackgroundColor("#1a1a1a") // Overall dark background
				.addList(todoList)
				.addList(inProgressList)
				.addList(doneList)
				.build();
	}
}

package io.vertigo.banshee.commands;

import java.util.UUID;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.board.ShinyBoardCard;
import io.vertigo.shiny.models.data.board.ShinyBoardList;

public class BoardSamples {

	public static class CrmInstallationBoard {
		public ShinyModel execute() {
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
					.addCard(card6)
					.build();

			return Shiny.board()
					.withName("Installation CRM")
					.withDescription("Tableau de bord pour le suivi de l'installation du nouveau CRM.")
					.withBackgroundColor("#0079bf")
					.addList(todoList)
					.addList(inProgressList)
					.addList(doneList)
					.build();
		}
	}
}

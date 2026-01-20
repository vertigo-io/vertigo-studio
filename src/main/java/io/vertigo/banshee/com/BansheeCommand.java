package io.vertigo.banshee.com;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyState;

/**
 * Le client envoie une commande au serveur
 */
public record BansheeCommand(
		String commandLine,
		UUID id, //facultatif id d'un model
		ShinyState state) { //facultatif nouvel état d'affichage d'un modèle 
}

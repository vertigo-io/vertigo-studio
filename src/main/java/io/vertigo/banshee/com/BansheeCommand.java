package io.vertigo.banshee.com;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyState;

public record BansheeCommand(
		String command,
		UUID id, //facultatif id d'un model
		ShinyState state) { //facultatif nouvel état d'affichage d'un modèle 
}

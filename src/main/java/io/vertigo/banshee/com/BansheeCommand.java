package io.vertigo.banshee.com;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyState;

/**
 * Le client envoie une commandeLine au serveur.
 */
public record BansheeCommand(
		String commandLine, // command + args
		UUID id, //facultatif id d'un model
		ShinyState state) { //facultatif nouvel état d'affichage d'un modèle 

	public String command() {
		int i = commandLine.indexOf(" ");
		return i > 0
				? commandLine.substring(0, i)
				: commandLine;
	}

	public String args() {
		int i = commandLine.indexOf(" ");
		return i > 0
				? commandLine.substring(i + 1)
				: "";
	}
}

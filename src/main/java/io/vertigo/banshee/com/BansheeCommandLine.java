package io.vertigo.banshee.com;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyState;

/**
 * Le client envoie une commandeLine au serveur.
 * 
 * Une commandLine est composée 
 * - d'une commande ( et éventuellement de sous-commandes) 
 * - d'args
 * 
 * 
 */
public record BansheeCommandLine(
		String commandLine, // commands[] + args
		UUID id, //facultatif id d'un model
		ShinyState state) { //facultatif nouvel état d'affichage d'un modèle 

	public BansheeCommandLine {
		Assertion.check().isNotBlank(commandLine);
	}

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

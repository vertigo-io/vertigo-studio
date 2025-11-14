package io.vertigo.banshee.com;

import io.vertigo.shiny.models.ShinyModel;

/**
 * Envoie une réponse du serveur au client.
 */
public record BansheeResult(
		BansheeAction action, //create or update
		ShinyModel model) {
}

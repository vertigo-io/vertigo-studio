package io.vertigo.banshee.com;

import io.vertigo.shiny.models.ShinyModel;

/**
 * CommandExceutor execute une commande et retourne un ShinyModel.
 */
public interface BansheeCommandExecutor {
	ShinyModel execute(BansheeCommandLine commandLine) throws Exception;
}

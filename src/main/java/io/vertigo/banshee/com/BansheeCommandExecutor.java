package io.vertigo.banshee.com;

import io.vertigo.shiny.models.ShinyModel;

public interface BansheeCommandExecutor {
	ShinyModel execute(BansheeCommand command) throws Exception;
}

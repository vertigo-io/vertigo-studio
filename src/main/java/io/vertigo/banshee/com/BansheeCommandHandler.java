package io.vertigo.banshee.com;

import java.util.HashMap;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.feedback.error.ShinyErrorBuilder;

/**
 * CommandHandler dispatche les commandes à l'executor idoine.
 * Les CommandExecutors auront été enregistrés dans le builder.
 * Attention les executors doivent être sans état car ils sont réutilisés d'une exécution à l'autre.
 * 
 */
public final class BansheeCommandHandler implements BansheeCommandExecutor {
	private final Map<String, BansheeCommandExecutor> _commandExecutors = new HashMap<>();

	BansheeCommandHandler(final Map<String, BansheeCommandExecutor> commandExecutors) {
		Assertion.check().isNotNull(commandExecutors);
		//---
		this._commandExecutors.putAll(commandExecutors);
	}

	public ShinyModel execute(final BansheeCommandLine commandLine) throws Exception {
		System.out.println("execute commandLine: " + commandLine.commandLine());
		return execute(commandLine, 0);
	}

	private ShinyModel execute(final BansheeCommandLine commandLine, int level) throws Exception {
		Assertion.check().isNotNull(commandLine);
		//---
		final var commandExecutor = _commandExecutors.get(commandLine.command(level));
		if (commandExecutor == null) {
			return new ShinyErrorBuilder().withText("Unknown command :" + commandLine.command(level)).build();
		}
		if (commandExecutor instanceof BansheeCommandHandler bch) {
			//cas des sous-commandes
			return bch.execute(commandLine, level + 1);
		} else {
			return commandExecutor.execute(commandLine);
		}
	}

}

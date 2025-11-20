package io.vertigo.banshee.com;

import java.util.HashMap;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.feedback.error.ShinyErrorBuilder;

public final class BansheeCommandHandler {
	private final Map<String, BansheeCommandExecutor> _commandExecutors = new HashMap<>();

	BansheeCommandHandler(final Map<String, BansheeCommandExecutor> commandExecutors) {
		Assertion.check().isNotNull(commandExecutors);
		//---
		this._commandExecutors.putAll(commandExecutors);
	}

	public ShinyModel execute(final BansheeCommand command) throws Exception {
		Assertion.check().isNotNull(command);
		//---
		final var commandExecutor = _commandExecutors.get(command.command());
		if (commandExecutor == null) {
			return new ShinyErrorBuilder().withText("unknown command :" + command.command()).build();
		}
		return commandExecutor.execute(command);
	}
}

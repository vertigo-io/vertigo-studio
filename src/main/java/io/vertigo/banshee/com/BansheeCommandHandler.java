package io.vertigo.banshee.com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.feedback.error.ShinyErrorBuilder;

public final class BansheeCommandHandler {
	private final Map<String, BansheeCommandExecutor> _commandExecutors = new HashMap<>();

	BansheeCommandHandler(final List<BansheeCommandExecutor> commandExecutors) {
		Assertion.check().isNotNull(commandExecutors);
		//---
		for (final BansheeCommandExecutor commandExecutor : commandExecutors) {
			final var previous = _commandExecutors.put(commandExecutor.getCommand(), commandExecutor);
			Assertion.check().isNull(previous, "a command '{0}' is already registered", commandExecutor.getCommand());
		}
	}

	public ShinyModel execute(final BansheeCommand command) throws Exception {
		final var commandExecutor = _commandExecutors.get(command.command());
		if (commandExecutor == null) {
			return new ShinyErrorBuilder().withText("unknown command :" + command.command()).build();
		}
		return commandExecutor.execute(command);
	}
}

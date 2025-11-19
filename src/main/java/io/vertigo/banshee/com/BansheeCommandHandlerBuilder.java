package io.vertigo.banshee.com;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyModel;

public final class BansheeCommandHandlerBuilder implements Builder<BansheeCommandHandler> {
	private final List<BansheeCommandExecutor> _commandExecutors = new ArrayList<>();

	public BansheeCommandHandlerBuilder addCommandExecutor(final BansheeCommandExecutor commandExecutor) {
		Assertion.check().isNotNull(commandExecutor);
		//---
		_commandExecutors.add(commandExecutor);
		return this;
	}

	public BansheeCommandHandlerBuilder addCommandExecutor(final String command, final Function<BansheeCommand, ShinyModel> commandFunction) {
		Assertion.check()
				.isNotBlank(command)
				.isNotNull(commandFunction);
		//---
		addCommandExecutor(new BansheeCommandExecutor() {

			@Override
			public String getCommand() {
				return command;
			}

			@Override
			public ShinyModel execute(final BansheeCommand bansheeCommand) {
				return commandFunction.apply(bansheeCommand);
			}
		});
		return this;
	}

	@Override
	public BansheeCommandHandler build() {
		return new BansheeCommandHandler(_commandExecutors);
	}
}

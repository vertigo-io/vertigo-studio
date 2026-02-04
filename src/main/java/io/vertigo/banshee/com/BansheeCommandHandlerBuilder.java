package io.vertigo.banshee.com;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyModel;

public final class BansheeCommandHandlerBuilder implements Builder<BansheeCommandHandler> {
	private final Map<String, BansheeCommandExecutor> _commandExecutors = new HashMap<>();

	public BansheeCommandHandlerBuilder addCommandExecutor(final String command, final BansheeCommandExecutor commandExecutor) {
		Assertion.check()
				.isNotBlank(command)
				.isNotNull(commandExecutor);
		//---
		_commandExecutors.put(command, commandExecutor);
		return this;
	}

	public BansheeCommandHandlerBuilder addCommandExecutor(final String command, final Callable<ShinyModel> callable) {
		Assertion.check()
				.isNotBlank(command)
				.isNotNull(callable);
		//---
		_commandExecutors.put(command, new BansheeCommandExecutor() {
			public ShinyModel execute(BansheeCommandLine commandLine) throws Exception {
				return callable.call();
			}
		});
		return this;
	}

	@Override
	public BansheeCommandHandler build() {
		return new BansheeCommandHandler(_commandExecutors);
	}
}

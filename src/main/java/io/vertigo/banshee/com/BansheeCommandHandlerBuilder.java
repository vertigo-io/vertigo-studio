package io.vertigo.banshee.com;

import java.util.HashMap;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

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

	//	public BansheeCommandHandlerBuilder addCommandExecutor(final String command, final Function<BansheeCommand, ShinyModel> commandFunction) {
	//		Assertion.check()
	//				.isNotBlank(command)
	//				.isNotNull(commandFunction);
	//		//---
	//		addCommandExecutor(command, new BansheeCommandExecutor() {
	//			@Override
	//			public ShinyModel execute(final BansheeCommand bansheeCommand) {
	//				return commandFunction.apply(bansheeCommand);
	//			}
	//		});
	//		return this;
	//	}

	@Override
	public BansheeCommandHandler build() {
		return new BansheeCommandHandler(_commandExecutors);
	}
}

package io.vertigo.banshee.servers;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.banshee.com.BansheeAction;
import io.vertigo.banshee.com.BansheeCommandHandler;
import io.vertigo.banshee.com.BansheeCommandLine;
import io.vertigo.banshee.com.BansheeResult;
import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

final class BansheeHandler {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private final BansheeCommandHandler commandHandler;

	BansheeHandler(@Nonnull BansheeCommandHandler commandHandler) {
		Assertion.check().isNotNull(commandHandler);
		//---
		this.commandHandler = commandHandler;
	}

	private static void sendEvent(final Consumer<String> webSocket, final BansheeResult event) {
		Assertion.check()
				.isNotNull(webSocket)
				.isNotNull(event);
		//---
		try {
			final String json = MAPPER.writeValueAsString(event);
			webSocket.accept(json);
			System.out.println("<<< sent : " + json);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	void handle(final Consumer<String> webSocket, final String event) {
		try {
			final BansheeCommandLine commandLine = MAPPER.readValue(event, BansheeCommandLine.class);
			//1.
			final ShinyModel model = execute(commandLine);
			//2.
			final BansheeAction action;
			action = commandLine.id() == null
					? BansheeAction.create
					: BansheeAction.update;
			sendEvent(webSocket, new BansheeResult(
					action,
					model));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private ShinyModel execute(final BansheeCommandLine commandLine) throws Exception {
		return commandHandler.execute(commandLine);
	}
}

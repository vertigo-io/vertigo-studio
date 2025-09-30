package io.vertigo.shell.server;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;

record BansheeMessage(
		BansheeAction action,
		String type,
		UUID id, //may be null
		String data) {
	public BansheeMessage {
		Assertion.check()
				.isNotBlank(type)
				//.isNotNull(id)
				.isNotNull(action)
				.isNotBlank(data);
	}
}

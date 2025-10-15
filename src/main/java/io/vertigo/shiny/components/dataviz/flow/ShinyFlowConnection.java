package io.vertigo.shiny.components.dataviz.flow;

import io.vertigo.core.lang.Assertion;

public record ShinyFlowConnection(
		String from,
		String to) {

	public ShinyFlowConnection {
		Assertion.check()
				.isNotBlank(from, "Connection 'from' cannot be blank")
				.isNotBlank(to, "Connection 'to' cannot be blank");
	}
}

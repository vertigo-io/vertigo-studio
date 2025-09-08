package io.vertigo.shell.ai;

import io.vertigo.shell.systems.env.EnvVar;

public enum AgentVar implements EnvVar {
	API_KEY("ai.api-key"),
	MODEL_NAME("ai.model-name");

	private final String key;

	AgentVar(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

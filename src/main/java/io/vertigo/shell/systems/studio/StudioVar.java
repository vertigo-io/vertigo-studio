package io.vertigo.shell.systems.studio;

import io.vertigo.shell.systems.env.EnvVar;

public enum StudioVar implements EnvVar {
	ROOT_PATH("studio.notebook");

	private final String key;

	StudioVar(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

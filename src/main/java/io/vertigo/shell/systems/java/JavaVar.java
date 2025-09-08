package io.vertigo.shell.systems.java;

import io.vertigo.shell.systems.env.EnvVar;

public enum JavaVar implements EnvVar {
	ROOT_PATH("java.rootpath");

	private final String key;

	JavaVar(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}

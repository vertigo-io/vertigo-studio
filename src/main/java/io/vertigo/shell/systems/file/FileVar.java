package io.vertigo.shell.systems.file;

import io.vertigo.shell.systems.env.EnvVar;

public enum FileVar implements EnvVar {
	ROOT_PATH("file.rootpath");

	private final String key;

	FileVar(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

package io.vertigo.shell.systems.photo;

import io.vertigo.shell.systems.env.EnvVar;

public enum PhotoVar implements EnvVar {
	ROOT_PATH("photo.rootpath");

	private final String key;

	PhotoVar(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

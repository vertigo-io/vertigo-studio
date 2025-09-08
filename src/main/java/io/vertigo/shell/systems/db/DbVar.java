package io.vertigo.shell.systems.db;

import io.vertigo.shell.systems.env.EnvVar;

public enum DbVar implements EnvVar {
	STORAGE("db.storage"),
	URL("db.url"),
	USER("db.user"),
	PASSWORD("db.password");

	private final String key;

	DbVar(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

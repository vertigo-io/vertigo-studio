package io.vertigo.shell.shiny.data.tree;

public enum ShinyIcon {
	FOLDER_OPEN("\uf07c"),
	FOLDER_CLOSED("\uf07b"),
	FILE("\uf15b"),
	DB("\uf1c0"),
	USER("\uf007"),
	SUCCESS("\uf00c"),
	ERROR("\uf00d"),
	WARNING("\uf071"),
	INFO("\uf05a"),
	QUESTION("\uf059"),
	CLOCK("\uf017"),
	ARROW_UP("\uf062"),
	ARROW_DOWN("\uf063"),
	ARROW_LEFT("\uf060"),
	ARROW_RIGHT("\uf061"),
	STAR("\uf005"),
	HEART("\uf004"),
	SMILEY("\uf118"),
	NONE("");

	private final String value;

	ShinyIcon(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

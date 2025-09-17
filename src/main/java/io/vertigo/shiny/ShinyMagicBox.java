package io.vertigo.shiny;

public final class ShinyMagicBox<O> {
	private volatile O magicValue;

	public void set(O value) {
		this.magicValue = value;
	}

	public O get() {
		return this.magicValue;
	}

}

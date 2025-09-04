package io.vertigo.shiny;

public final class ShinyTheme {
	private boolean asciiTheme = false; //vs unicode
	private boolean squareTheme = false; //vs rounded

	public ShinyTheme ascii(boolean ascii) {
		asciiTheme = ascii;
		return this;
	}

	public ShinyTheme unicode(boolean unicode) {
		asciiTheme = !unicode;
		return this;
	}

	public boolean ascii() {
		return asciiTheme;
	}

	public ShinyTheme square(boolean square) {
		squareTheme = square;
		return this;
	}

	public ShinyTheme rounded(boolean rounded) {
		squareTheme = !rounded;
		return this;
	}

	public boolean square() {
		return squareTheme;
	}
}

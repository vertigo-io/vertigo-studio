package io.vertigo.shiny.models.text.figlet;

public enum ShinyFigletFont {
	BANNER("banner.flf"),
	BIG("big.flf"),
	BLOCK("block.flf"),
	BUBBLE("bubble.flf"),
	DIGITAL("digital.flf"),
	IVRIT("ivrit.flf"),
	LEAN("lean.flf"),
	MINI("mini.flf"),
	MNEMONIC("mnemonic.flf"),
	SCRIPT("script.flf"),
	SHADOW("shadow.flf"),
	SLANT("slant.flf"),
	SMALL("small.flf"),
	SMSCRIPT("smscript.flf"),
	SMSHADOW("smshadow.flf"),
	SMSLANT("smslant.flf"),
	STANDARD("standard.flf"),
	TERMINAL("term.flf");

	private final String fileName;

	ShinyFigletFont(final String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
}

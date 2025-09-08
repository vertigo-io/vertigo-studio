package io.vertigo.shiny;

import java.io.PrintWriter;

public final class ShinyWriter {
	//	private final StringBuilder sb = new StringBuilder();
	private final PrintWriter writer;

	ShinyWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public ShinyWriter print(String s) {
		writer.append(s);
		return this;
	}

	public ShinyWriter println() {
		writer.println();
		return this;
	}

	public ShinyWriter println(String s) {
		writer.println(s);
		return this;
	}

	public void flush() {
		writer.flush();
	}

	//	public static Ansi ansi() {
	//		return new Ansi();
	//	}
	//
	//	public Ansi fg(final ShinyColor color) {
	//		sb.append(color.fg());
	//		return this;
	//	}
	//
	//	public Ansi bg(final ShinyColor color) {
	//		sb.append(color.bg());
	//		return this;
	//	}
	//
	//	public Ansi effect(final ShinyEffects effect) {
	//		sb.append(effect.getEffect());
	//		return this;
	//	}
	//
	//	public Ansi a(final String text) {
	//		sb.append(text);
	//		return this;
	//	}
	//
	//	public Ansi reset() {
	//		sb.append(ShinyReset.ALL);
	//		return this;
	//	}
	//
	//	@Override
	//	public String toString() {
	//		return sb.toString();
	//	}

	public ShinyWriter printf(String format, Object... args) {
		writer.printf(format, args);
		return this;
	}
}

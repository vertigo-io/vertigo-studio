package io.vertigo.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.renderers.ShinySuperRenderer;

public final class ShinyRenderer {
	private PrintWriter writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
	private static final ShinyRenderer INSTANCE = new ShinyRenderer();
	private static ShinySuperRenderer RENDERER = new ShinySuperRenderer();
	private final ShinyTheme theme = new ShinyTheme();

	private ShinyRenderer() {
		writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
	}

	public static void withWriter(PrintWriter printWriter) {
		INSTANCE.writer = printWriter;
	}

	public static ShinyWriter writer() {
		return new ShinyWriter(INSTANCE.writer);
	}

	public static <S extends ShinyModel> void render(S component) {
		RENDERER.render(component);
	}

	public static ShinyTheme theme() {
		return INSTANCE.theme;
	}

	public PrintWriter getWriter() {
		return writer;
	}

}

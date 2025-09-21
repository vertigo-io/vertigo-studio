package io.vertigo.shiny.components.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.text.textpath.ShinyTextPathStyle;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyTextPathTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicPaths(writer);
		testCustomColors(writer);
		testCustomSeparator(writer);
		testSeparatorColor(writer); // New test case
		testEdgeCases(writer);
	}

	private static void testBasicPaths(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Paths ---"));
		Shiny.render(
				Shiny.textPath()
						.withPath("/home/user/documents/report.pdf")
						.build());
		Shiny.render(
				Shiny.textPath()
						.withPath("C:\\Program Files\\Java\\jdk-17\\bin")
						.withSeparator("\\")
						.build());
		Shiny.render(
				Shiny.textPath()
						.withPath("folder/subfolder/file.txt")
						.build());
		writer.println();
	}

	private static void testCustomColors(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Custom Colors ---"));
		Shiny.render(
				Shiny.textPath()
						.withPath("/app/config/settings.json")
						.withStyle(new ShinyTextPathStyle()
								.withRootColor(ShinyColors.RED)
								.withNodeColor(ShinyColors.YELLOW)
								.withLeafColor(ShinyColors.GREEN))
						.build());
		Shiny.render(
				Shiny.textPath()
						.withPath("/var/log/syslog")
						.withStyle(new ShinyTextPathStyle()
								.withRootColor(ShinyColors.MAGENTA)
								.withNodeColor(ShinyColors.CYAN)
								.withLeafColor(ShinyColors.WHITE))
						.build());
		writer.println();
	}

	private static void testCustomSeparator(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Custom Separator ---"));
		Shiny.render(
				Shiny.textPath()
						.withPath("data.csv")
						.withSeparator(".")
						.withStyle(new ShinyTextPathStyle()
								.withRootColor(ShinyColors.WHITE)
								.withNodeColor(ShinyColors.BLUE)
								.withLeafColor(ShinyColors.RED))
						.build());
		Shiny.render(
				Shiny.textPath()
						.withPath("item1->item2->item3")
						.withSeparator("->")
						.withStyle(new ShinyTextPathStyle()
								.withRootColor(ShinyColors.WHITE)
								.withNodeColor(ShinyColors.GREEN)
								.withLeafColor(ShinyColors.YELLOW))
						.build());
		writer.println();
	}

	private static void testSeparatorColor(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Separator Color ---"));
		Shiny.render(
				Shiny.textPath()
						.withPath("/path/to/my/file.txt")
						.withStyle(new ShinyTextPathStyle()
								.withSeparatorColor(ShinyColors.RED))
						.build());
		Shiny.render(
				Shiny.textPath()
						.withPath("C:\\Users\\Admin\\Desktop")
						.withSeparator("\\")
						.withStyle(new ShinyTextPathStyle()
								.withSeparatorColor(ShinyColors.BLUE))
						.build());
		writer.println();
	}

	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Edge Cases ---"));
		Shiny.render(
				Shiny.textPath()
						.withPath("/singlefile.txt")
						.build()); // Root and leaf
		Shiny.render(
				Shiny.textPath()
						.withPath("justfile.txt")
						.build()); // Just leaf
		Shiny.render(
				Shiny.textPath()
						.withPath("/")
						.build()); // Just root
		Shiny.render(
				Shiny.textPath()
						.withPath("///a/b///c.txt")
						.build()); // Multiple separators
		writer.println();
	}
}
